package ru.itis.taskmanager.security.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.repositories.TaskUserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TaskUserRepository usersRepository;

    public CustomUserDetailsService(TaskUserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TaskUser> userCandidate = usersRepository.findTaskUserByUsername(username);

        userCandidate.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        return buildForAuth(userCandidate.get());
    }

    private User buildForAuth(TaskUser user) {
        return new CustomUser(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority("user")), user);
    }
}
