package me.code.fulldemo.controllers;

import me.code.fulldemo.dtos.UserCreation;
import me.code.fulldemo.dtos.UserPayload;
import me.code.fulldemo.exceptions.UserAlreadyExistsException;
import me.code.fulldemo.models.User;
import me.code.fulldemo.security.UserObject;
import me.code.fulldemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/register")
    public UserPayload registerUser(@RequestBody UserCreation creation)
            throws UserAlreadyExistsException
    {
        var user = userService.registerUser(creation.getUsername(), creation.getPassword(), creation.isAdmin());
        return UserPayload.fromUser(user);
    }

    @GetMapping("/info")
    public UserPayload info(@AuthenticationPrincipal UserObject user) {
        return UserPayload.fromUser(user.getUser());
    }

}
