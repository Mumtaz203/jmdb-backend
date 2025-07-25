package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import com.umutavci.imdb.infrastructure.persistence.entities.Actor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActorResponse extends Base {
    private String name;
    private String role;
    private String imageUrl;
    private LocalDate birthDate; 

    public static ActorResponse fromEntity(Actor actor) {
        ActorResponse response = new ActorResponse();
        response.setId(actor.getId());
        response.setName(actor.getName());
        response.setRole(actor.getRole());
        response.setImageUrl(actor.getImageUrl());
        response.setBirthDate(actor.getBirthDate());
        return response;
    }
}
