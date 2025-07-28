package com.umutavci.imdb.domain.resolvers.mutations;

import com.umutavci.imdb.application.services.MovieService;
import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieMutationResolver {
    @Autowired
    private final MovieService movieService;

    public MovieMutationResolver(MovieService movieService) {
        this.movieService = movieService;
    }
    @MutationMapping
    public MovieResponse createMovie(@Argument MovieInput input){
        return movieService.create(input);
    }
    @MutationMapping
    public boolean deleteMovie(@Argument Long id){
        return movieService.delete(id);
    }
    @MutationMapping
    public MovieResponse updateMovie(@Argument Long id, @Argument MovieInput input){
        return movieService.update(id, input);
    }
    @MutationMapping
    public List<ActorResponse> addActorInMovie(@Argument Long movieId, @Argument Long actorId){
        return movieService.addActorInMovie(movieId, actorId);
    }
    @MutationMapping
    public List<ActorResponse> removeActorInMovie(@Argument Long movieId, @Argument Long actorId){
        return movieService.removeActorInMovie(movieId, actorId);
    }
    @MutationMapping
    public List<ReviewResponse> addReviewInMovie(@Argument Long movieId, @Argument Long reviewId){
        return movieService.addReviewInMovie(movieId, reviewId);
    }

    @MutationMapping
    public List<MovieResponse> addMovieToWatchList(@Argument Long movieId, @Argument Long userId){
        return movieService.addMovieToWatchList(movieId, userId);
    }
}
