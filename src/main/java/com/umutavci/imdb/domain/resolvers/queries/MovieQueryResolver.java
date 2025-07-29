package com.umutavci.imdb.domain.resolvers.queries;

import com.umutavci.imdb.application.services.MovieService;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieQueryResolver {
    @Autowired
    private final MovieService movieService;

    public MovieQueryResolver(MovieService movieService) {
        this.movieService = movieService;
    }
    @QueryMapping
    public MovieResponse getMovie(@Argument Long id){
        return movieService.getSingle(id);
    }
    @QueryMapping
    public List<MovieResponse> getAllMovies(){
        return movieService.getAll();
    }
    @QueryMapping
    public List<MovieResponse> searchMovieByName(@Argument String name){
        return movieService.searchMovieByName(name);
    }
    @QueryMapping
    public List<MovieResponse> sortMovieByDescName(){
        return movieService.sortMovieByDescName();
    }
    @QueryMapping
    public List<MovieResponse> sortMovieByBetterReviewPoint(){
        return movieService.sortMovieByBetterReviewPoint();
    }
        @QueryMapping
    public List<ActorResponse> showAllActorsInMovie(@Argument Long movieId){
        return movieService.showAllActorsInMovie(movieId);
    }
    @QueryMapping
    public Float findAverageRankingInMovie(@Argument Long movieId){
        return movieService.findAverageRankingInMovie(movieId);
    }

    @QueryMapping
    public List<ReviewResponse> showAllReviewsInMovie(@Argument Long movieId){
        return movieService.showAllReviewsInMovie(movieId);
    }
    @QueryMapping
    public List<MovieResponse> showWatchList(@Argument Long userId){
        return movieService.showWatchList(userId);
    }

}
