package me.code.fulldemo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreation {

    private String username, password;
    private boolean admin;

}
