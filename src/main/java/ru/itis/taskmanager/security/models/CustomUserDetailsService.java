package ru.itis.taskmanager.security.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.repositories.TaskUserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaskUserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TaskUser> userCandidate = usersRepository.findTaskUserByUsername(username);

        userCandidate.orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        return new CustomUserDetails(userCandidate.get());
    }
}
