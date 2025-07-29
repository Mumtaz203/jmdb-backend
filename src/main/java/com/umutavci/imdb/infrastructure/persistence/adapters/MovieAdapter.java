package com.umutavci.imdb.infrastructure.persistence.adapters;

import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.domain.ports.repositories.IMovieRepository;
import com.umutavci.imdb.infrastructure.persistence.entities.Actor;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import com.umutavci.imdb.infrastructure.persistence.entities.Review;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import com.umutavci.imdb.infrastructure.persistence.mapper.ActorMapper;
import com.umutavci.imdb.infrastructure.persistence.mapper.MovieMapper;
import com.umutavci.imdb.infrastructure.persistence.mapper.ReviewMapper;
import com.umutavci.imdb.infrastructure.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
public class MovieAdapter implements IMovieRepository {

    @Autowired
    private final MovieJpaRepository movieJpaRepository;

    private final ReviewJpaRepository reviewJpaRepository;

    private final ActorJpaRespository actorJpaRespository;

    @Autowired
    private final MovieMapper movieMapper;

    private final DirectorJpaRepository directorJpaRepository;

    private final ActorMapper actorMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserJpaRepository userJpaRepository;

    public MovieAdapter(MovieJpaRepository movieJpaRepository, ReviewJpaRepository reviewJpaRepository, ActorJpaRespository actorJpaRespository, MovieMapper movieMapper, DirectorJpaRepository directorJpaRepository, ActorMapper actorMapper) {
        this.movieJpaRepository = movieJpaRepository;
        this.reviewJpaRepository = reviewJpaRepository;
        this.actorJpaRespository = actorJpaRespository;
        this.movieMapper = movieMapper;
        this.directorJpaRepository = directorJpaRepository;
        this.actorMapper = actorMapper;
    }

    @Override
    public MovieResponse getSingle(Long id) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAll() {
        return movieJpaRepository.findAll()
                .stream()
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public MovieResponse create(MovieInput movieInput) {
        Movie movie = movieMapper.toMovie(movieInput);
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        Movie savedMovie = movieJpaRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);
    }

    @Override
    public MovieResponse update(Long id, MovieInput movieInput) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieInput.getTitle());
        movie.setReleaseDate(movieInput.getReleaseDate());
        movie.setDescription(movieInput.getDescription());
        movie.setDirector(directorJpaRepository.findById(movieInput.getDirectorId()).orElseThrow());
        movie.setGenre(movieInput.getGenre());
        movie.setUpdatedAt(LocalDateTime.now());
        Movie savedMovie = movieJpaRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);
    }

    @Override
    public boolean delete(Long id) {
        Movie movie = movieJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieJpaRepository.delete(movie);
        return !movieJpaRepository.findById(id).isPresent();
    }

    @Override
    public List<MovieResponse> searchMovieByName(String name) {
        return movieJpaRepository.findAll()
                .stream()
                .filter(m -> m.getTitle().toLowerCase().contains(name.toLowerCase()))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> sortMovieByDescName() {
        return movieJpaRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> sortMovieByBetterReviewPoint() {

        return movieJpaRepository.findAll()
                .stream()
                .sorted(new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        return findAverageRankingInMovie(o2.getId()).compareTo(findAverageRankingInMovie(o1.getId()));
                    }
                })
                .map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<ActorResponse> addActorInMovie(Long movieId, Long actorId) {
        Movie movie = movieJpaRepository.findById(movieId).orElseThrow();
        Actor actor = actorJpaRespository.findById(actorId).orElseThrow();
        movie.getActors().add(actor);
        movieJpaRepository.save(movie);
        return movieJpaRepository.findById(movieId).orElseThrow()
                .getActors()
                .stream().map(actorMapper::toActorResponse)
                .toList();
    }

    @Override
    public List<ActorResponse> removeActorInMovie(Long movieId, Long actorId) {
        Movie movie = movieJpaRepository.findById(movieId).orElseThrow();
        Actor actor = actorJpaRespository.findById(actorId).orElseThrow();
        movie.getActors().remove(actor);
        movieJpaRepository.save(movie);
        return movieJpaRepository.findById(movieId).orElseThrow()
                .getActors()
                .stream().map(actorMapper::toActorResponse)
                .toList();
    }

    @Override
    public List<ActorResponse> showAllActorsInMovie(Long movieId) {
        return movieJpaRepository.findById(movieId).orElseThrow()
                .getActors()
                .stream().map(actorMapper::toActorResponse)
                .toList();
    }

    @Override
    public Float findAverageRankingInMovie(Long movieId) {
        List<Review> reviews = reviewJpaRepository
                .findAll()
                .stream()
                .filter(m -> m.getMovie().getId() == movieId)
                .toList();
        double sum = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        return (float) sum / reviews.size();
    }

    @Override
    public List<ReviewResponse> addReviewInMovie(Long movieId, Long reviewId) {
        Movie movie = movieJpaRepository.findById(movieId).orElseThrow();
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow();
        movie.getReviewList().add(review);
        movieJpaRepository.save(movie);
        return movieJpaRepository.findById(movieId).orElseThrow()
                .getReviewList()
                .stream().map(reviewMapper::toReviewResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> showAllReviewsInMovie(Long movieId) {
        return movieJpaRepository.findById(movieId).orElseThrow()
                .getReviewList()
                .stream().map(reviewMapper::toReviewResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> addMovieToWatchList(Long movieId, Long userId) {
        Movie movie = movieJpaRepository.findById(movieId).orElseThrow();
        User user = userJpaRepository.findById(userId).orElseThrow();
        user.getMovies().add(movie);
        userJpaRepository.save(user);
        return userJpaRepository.findById(userId).orElseThrow()
                .getMovies()
                .stream().map(movieMapper::toMovieResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> showWatchList(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow()
                .getMovies()
                .stream().map(movieMapper::toMovieResponse)
                .toList();
    }

}
