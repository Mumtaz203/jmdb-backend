package com.umutavci.imdb.application.services;
//2
import com.umutavci.imdb.domain.models.in.DirectorInput;
import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.DirectorResponse;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import com.umutavci.imdb.domain.ports.repositories.IBaseRepository;
import com.umutavci.imdb.domain.ports.repositories.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements BaseService<MovieInput, MovieResponse>{
    @Autowired
    private final IMovieRepository repository;

    public MovieService(IMovieRepository repository) {
        this.repository = repository;
    }
    @Override
    public MovieResponse getSingle(Long id){ return repository.getSingle(id); }
    @Override
    public List<MovieResponse> getAll(){ return repository.getAll(); }
    @Override
    public MovieResponse create(MovieInput input){ return repository.create(input); }
    @Override
    public MovieResponse update(Long id, MovieInput input){ return repository.update(id, input); }
    @Override
    public boolean delete(Long id){ return repository.delete(id); }

    public List<MovieResponse> searchMovieByName(String name){ return repository.searchMovieByName(name); }
    public List<MovieResponse> sortMovieByDescName(){ return repository.sortMovieByDescName(); }
    public List<MovieResponse> sortMovieByBetterReviewPoint() { return repository.sortMovieByBetterReviewPoint(); }
    public List<ActorResponse> addActorInMovie(Long movieId, Long actorId){ return repository.addActorInMovie(movieId, actorId); }
    public List<ActorResponse> removeActorInMovie(Long movieId, Long actorId){ return repository.removeActorInMovie(movieId, actorId); }
    public List<ActorResponse> showAllActorsInMovie(Long movieId){ return repository.showAllActorsInMovie(movieId); }
    public Double findAverageRankingInMovie(Long movieId){ return repository.findAverageRankingInMovie(movieId); }

    public List<ReviewResponse> addReviewInMovie(Long movieId,Long reviewId){return repository.addReviewInMovie(movieId,reviewId); }
    public List<ReviewResponse> showAllReviewsInMovie(Long movieId){ return repository.showAllReviewsInMovie(movieId); }

    public List <MovieResponse> addMovieToWatchList(Long movieId,Long userId){return repository.addMovieToWatchList(movieId,userId); }
    public List <MovieResponse> showWatchList(Long userId){ return repository.showWatchList(userId); }

}
