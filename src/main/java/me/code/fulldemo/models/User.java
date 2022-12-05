package me.code.fulldemo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document
public class User {

    @Id
    private UUID id;

    private String username;
    private String password;
    private boolean admin;

    public User(String username, String password, boolean admin) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

}
