package com.umutavci.imdb.domain.resolvers.queries;

import com.umutavci.imdb.application.services.UserService;
import com.umutavci.imdb.domain.models.in.LoginInput;
import com.umutavci.imdb.domain.models.out.MovieResponse;
import com.umutavci.imdb.domain.models.out.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserQueryResolver {
    @Autowired
    private final UserService userService;

    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public UserResponse getUser(@Argument Long id){
        return userService.getSingle(id);
    }
    @QueryMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAll();
    }

    @QueryMapping
    public List<MovieResponse> getUserWatchlist(@Argument Long userId){
        return userService.getWatchlist(userId);
    }
}
