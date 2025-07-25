package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieResponse extends Base {
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private String description;
    private Double rating;
    private Long directorId;
    private String imageUrl;
    private String duration;
    private List<ActorResponse> stars;

    public static MovieResponse fromEntity(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setReleaseDate(movie.getReleaseDate());
        response.setGenre(movie.getGenre());
        response.setDescription(movie.getDescription());
        response.setDirectorId(movie.getDirector() != null ? movie.getDirector().getId() : null);
        response.setImageUrl(movie.getImageUrl());
        response.setDuration(movie.getDuration());

        if (movie.getActors() != null) {
            response.setStars(movie.getActors().stream()
                    .map(ActorResponse::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            response.setStars(List.of());
        }
        return response;
    }
}
