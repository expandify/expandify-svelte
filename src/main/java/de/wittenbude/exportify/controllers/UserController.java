package de.wittenbude.exportify.controllers;

import de.wittenbude.exportify.dto.PrivateUserSchema;
import de.wittenbude.exportify.models.converter.UserConverter;
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
    public PrivateUserSchema me() {
        return UserConverter.toDTO(userService.getMe());
    }
}
