package de.wittenbude.exportify.http.controller;

import de.wittenbude.exportify.models.PrivateUser;
import de.wittenbude.exportify.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public PrivateUser me() {
        return userService.getMe();
    }
}
