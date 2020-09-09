package ru.itis.taskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.UserDto;
import ru.itis.taskmanager.repositories.TaskUserRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TaskUserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody TaskUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(UserDto.from(user));
    }
}
