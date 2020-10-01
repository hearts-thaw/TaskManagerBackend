package ru.itis.taskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.GroupDto;
import ru.itis.taskmanager.models.dto.UserDto;
import ru.itis.taskmanager.repositories.SignUpRepository;
import ru.itis.taskmanager.services.FrothService;
import ru.itis.taskmanager.services.GroupService;

import javax.validation.Valid;
import java.util.List;

@Profile("jdbc")
@RestController
@RequestMapping("/signUp")
public class SignUpControllerJdbcTemplate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SignUpRepository signUpRepository;

    private final GroupService groupService;

    private final FrothService frothService;

    public SignUpControllerJdbcTemplate(SignUpRepository signUpRepository, GroupService groupService, FrothService frothService) {
        this.signUpRepository = signUpRepository;
        this.groupService = groupService;
        this.frothService = frothService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody TaskUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        TaskUser newUser = signUpRepository.save(user);

        Group newGroup = GroupDto.back(groupService.create(newUser));

        frothService.add(Froth.builder()
                .name("main")
                .build(), newGroup.getId());


        user.setGroups(List.of(newGroup));

        return ResponseEntity.ok(UserDto.from(user));
    }
}
