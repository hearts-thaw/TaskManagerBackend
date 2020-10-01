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
import ru.itis.taskmanager.models.dto.UserDto;
import ru.itis.taskmanager.repositories.FrothRepositoryDataRest;
import ru.itis.taskmanager.repositories.GroupRepositoryDataRest;
import ru.itis.taskmanager.repositories.TaskUserRepository;

import javax.validation.Valid;
import java.util.List;

@Profile("jpa")
@RestController
@RequestMapping("/signUp")
public class SignUpControllerDataRest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final TaskUserRepository taskUserRepository;

    private final GroupRepositoryDataRest groupRepository;

    private final FrothRepositoryDataRest frothRepository;

    public SignUpControllerDataRest(TaskUserRepository taskUserRepository, GroupRepositoryDataRest groupRepository, FrothRepositoryDataRest frothRepository) {
        this.taskUserRepository = taskUserRepository;
        this.groupRepository = groupRepository;
        this.frothRepository = frothRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody TaskUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        TaskUser newUser = taskUserRepository.save(user);

        frothRepository.save(Froth.builder()
                .name("main")
                .group(
                        groupRepository.save(Group.builder()
                                .name("maingr")
                                .taskusers(List.of(newUser))
                                .build()
                        )
                )
                .build());
        return ResponseEntity.ok(UserDto.from(user));
    }
}
