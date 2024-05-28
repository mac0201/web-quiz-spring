package engine.controller;

import engine.model.dto.UserRegistrationDTO;
import engine.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Defines a REST controller for managing users. It handles various HTTP requests related to user operations.
 */
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegistrationDTO dto) {
        userService.register(dto);
    }
}
