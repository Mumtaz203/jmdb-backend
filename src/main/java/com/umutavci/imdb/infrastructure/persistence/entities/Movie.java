package com.umutavci.imdb.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie extends BaseEntity{
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private String description;
    @ManyToOne
    @JoinColumn(name = "director")
    private Director director;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviewList;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;
    @ManyToMany(mappedBy = "movies")
    private List<User> usersWatching;

}
