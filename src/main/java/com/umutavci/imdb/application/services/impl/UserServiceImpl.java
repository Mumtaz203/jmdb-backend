package com.umutavci.imdb.application.services.impl;

import com.umutavci.imdb.application.services.UserService;
import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.UserResponse;
import com.umutavci.imdb.infrastructure.persistence.entities.Movie;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import com.umutavci.imdb.infrastructure.persistence.repositories.MovieJpaRepository;
import com.umutavci.imdb.infrastructure.persistence.repositories.UserJpaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final MovieJpaRepository movieJpaRepository;

    public UserServiceImpl(UserJpaRepository userJpaRepository, MovieJpaRepository movieJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.movieJpaRepository = movieJpaRepository;
    }

    @Override
    public UserResponse getSingle(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return UserResponse.fromEntity(user);
    }

    @Override
    public List<UserResponse> getAll() {
        return userJpaRepository.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse create(UserInput input) {
        User newUser = new User();
        User savedUser = userJpaRepository.save(newUser);
        return UserResponse.fromEntity(savedUser);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (userJpaRepository.existsById(id)) {
            userJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserInput input) {
        User existingUser = userJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        User updatedUser = userJpaRepository.save(existingUser);
        return UserResponse.fromEntity(updatedUser);
    }

    @Override
    @Transactional
    public List<MovieResponse> getWatchlist(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return user.getWatchlistMovies().stream()
                .map(MovieResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addMovieToWatchlist(Long userId, Long movieId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Movie movie = movieJpaRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        user.addMovieToWatchlist(movie);
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void removeMovieFromWatchlist(Long userId, Long movieId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Movie movie = movieJpaRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        user.removeMovieFromWatchlist(movie);
        userJpaRepository.save(user);
    }
}
