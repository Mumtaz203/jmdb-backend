package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import com.umutavci.imdb.infrastructure.persistence.entities.User;
import lombok.Data;

@Data
public class UserResponse extends Base {
    private String username;
    private String email;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
