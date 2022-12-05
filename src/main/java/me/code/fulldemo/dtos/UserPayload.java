package me.code.fulldemo.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.code.fulldemo.models.User;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPayload {

    private final UUID id;

    private String username;
    private boolean admin;

    public static UserPayload fromUser(User user) {
        var payload = new UserPayload(user.getId());
        payload.setAdmin(user.isAdmin());
        payload.setUsername(user.getUsername());

        return payload;
    }

}
