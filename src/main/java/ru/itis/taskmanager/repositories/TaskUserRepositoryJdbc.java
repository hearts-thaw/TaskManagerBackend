package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.TaskUser;

import java.util.Objects;
import java.util.Optional;

@Profile("jdbc")
@Repository
public class TaskUserRepositoryJdbc implements TaskUserRepository {

    //    language=sql
    private static final String Q_FIND_USER_BY_USERNAME =
            "SELECT * FROM taskusers WHERE username = ? ;";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TaskUser> userRowMapper = (rs, rowNum) -> TaskUser.builder()
            .id(rs.getLong("userid"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .role(rs.getString("role"))
            .build();

    public TaskUserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TaskUser> findTaskUserByUsername(String username) {
        return Optional.of(Objects.requireNonNull(jdbcTemplate.queryForObject(Q_FIND_USER_BY_USERNAME, userRowMapper, username)));
    }

    @Override
    public TaskUser save(TaskUser taskUser) {
        return null;
    }
}
