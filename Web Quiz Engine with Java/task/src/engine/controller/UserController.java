package engine.controller;

import engine.model.User;
import engine.model.dto.UserRegistrationDTO;
import engine.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/user")
    public @ResponseBody String userInfo(@AuthenticationPrincipal User user) {
        return userService.userInfo(user);
    }
}
