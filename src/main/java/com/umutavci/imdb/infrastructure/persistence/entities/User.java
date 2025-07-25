package com.umutavci.imdb.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String email;
    private String pass;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_watchlist",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> watchlistMovies = new ArrayList<>();

    public void addMovieToWatchlist(Movie movie) {
        if (movie != null && !this.watchlistMovies.contains(movie)) {
            this.watchlistMovies.add(movie);
        }
    }

    public void removeMovieFromWatchlist(Movie movie) {
        if (movie != null && this.watchlistMovies.contains(movie)) {
            this.watchlistMovies.remove(movie);
        }
    }
}
