package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.entities.User;
import recipes.services.UserDetailsServiceImpl;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")

    public void registerUser(@RequestBody @Valid User user) {
        System.out.println("I RECEIVED USER FROM REGISTER REQUEST");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        this.userDetailsService.save(user);

    }

}
