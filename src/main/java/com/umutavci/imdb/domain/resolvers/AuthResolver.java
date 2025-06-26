package com.umutavci.imdb.domain.resolvers;

import com.umutavci.imdb.domain.models.in.LoginInput;
import com.umutavci.imdb.domain.models.out.LoginResponse;
import com.umutavci.imdb.infrastructure.persistence.adapters.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class AuthResolver {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserAdapter userAdapter;

    public AuthResolver(AuthenticationManager authenticationManager, UserAdapter userAdapter) {
        this.authenticationManager = authenticationManager;
        this.userAdapter = userAdapter;
    }

    @MutationMapping
    public LoginResponse login(@Argument LoginInput input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPass())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(authentication.isAuthenticated());
        if(authentication.isAuthenticated()){
            loginResponse.setId(userAdapter.getUserByEmail(input.getEmail()).getId());
        }
        return loginResponse;
    }

    @QueryMapping
    public String me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Anonym User";
        }
        return authentication.getName();
    }
}
