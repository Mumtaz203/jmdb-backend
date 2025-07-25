package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data
public class LoginResponse extends Base {
    private Long id;
    private Boolean success;
    private String token;
}
