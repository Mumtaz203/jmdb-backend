package com.umutavci.imdb.domain.resolvers.mutations;

import com.umutavci.imdb.application.services.UserService;
import com.umutavci.imdb.domain.models.in.UserInput;
import com.umutavci.imdb.domain.models.out.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class UserMutationResolver {
    @Autowired
    private final UserService userService;

    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public UserResponse createUser(@Argument UserInput input){
        return userService.create(input);
    }
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean deleteUser(@Argument Long id){
        return userService.delete(id);
    }
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponse updateUser(@Argument Long id, @Argument UserInput input){
        return userService.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Boolean addMovieToUserWatchlist(@Argument Long userId, @Argument Long movieId){
        userService.addMovieToWatchlist(userId, movieId);
        return true;
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public Boolean removeMovieFromUserWatchlist(@Argument Long userId, @Argument Long movieId){
        userService.removeMovieFromWatchlist(userId, movieId);
        return true;
    }
}
