package com.umutavci.imdb.domain.ports.repositories;
//1
import com.umutavci.imdb.domain.models.in.MovieInput;
import com.umutavci.imdb.domain.models.out.ActorResponse;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.ReviewResponse;

import java.util.List;

public interface IMovieRepository extends IBaseRepository<MovieInput, MovieResponse>{
    List<MovieResponse> searchMovieByName(String name);
    List<MovieResponse> sortMovieByDescName();
    List<MovieResponse> sortMovieByBetterReviewPoint();
    List<ActorResponse> addActorInMovie(Long movieId, Long actorId);
    List<ActorResponse> removeActorInMovie(Long movieId, Long actorId);
    List<ActorResponse> showAllActorsInMovie(Long movieId);
    Double findAverageRankingInMovie(Long movieId);

    List<ReviewResponse> addReviewInMovie(Long movieId, Long reviewId);
    List<ReviewResponse> showAllReviewsInMovie(Long movieId);

}
