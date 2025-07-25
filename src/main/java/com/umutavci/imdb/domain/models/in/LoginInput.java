package com.umutavci.imdb.domain.models.in;

import com.umutavci.imdb.domain.models.Base;
import lombok.Data;

@Data
public class LoginInput extends Base {
    private String email;
    private String pass;



    public String getEmail() {return email;}
    public String getPass() {return pass;}
    public void setEmail(String email) {this.email = email;}
    public void setPass(String pass) {this.pass = pass;}
}
