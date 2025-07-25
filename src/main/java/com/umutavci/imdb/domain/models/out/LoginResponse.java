package com.umutavci.imdb.domain.models.out;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data
public class LoginResponse extends Base {
    private Long id;
    private Boolean success;

    public LoginResponse(Long id, Boolean success) {
        this.id = id;
        this.success = success;
    }
    public Long getId() {
        return id;
    }

    public Boolean isSuccess() {
        return success;
    }
}
