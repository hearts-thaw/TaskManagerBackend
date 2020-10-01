package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.GroupDto;
import ru.itis.taskmanager.models.dto.UserForGroupDto;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Profile("jdbc")
@Repository
public class GroupRepositoryJdbc implements GroupRepository {
    private final static String Q_SELECT_BY_USER_ID =
            "SELECT g.*, u.userid, u.username FROM groups g " +
                    "JOIN groups_taskusers gt on g.groupid = gt.group_id " +
                    "JOIN taskusers u on u.userid = gt.taskuser_id " +
                    "WHERE taskuser_id = ?;";

    private static final String Q_INSERT_GROUP =
            "INSERT INTO groups (groupname)" +
                    "VALUES (?);";

    private final JdbcTemplate jdbcTemplate;

    public GroupRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<GroupDto> groupRefRowMapper = (rs, rowNum) -> GroupDto.from(Group.builder()
            .id(rs.getLong("groupid"))
            .name(rs.getString("groupname"))
            .build(), new UserForGroupDto(
            rs.getLong("userid"),
            rs.getString("username")));

    @Override
    public Optional<List<GroupDto>> findAllByTaskuser_Id(Long taskusers_id) {
        return Optional.of(Objects.requireNonNull(
                jdbcTemplate.query(Q_SELECT_BY_USER_ID, groupRefRowMapper, taskusers_id)));
    }

    @Override
    public Optional<GroupDto> save(Group group, TaskUser user) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(Q_INSERT_GROUP, new String[]{"groupid"});

            stmt.setString(1, group.getName());

            return stmt;
        }, holder);

        Long id = Objects.requireNonNull(holder.getKey()).longValue();

        group.setId(id);

        jdbcTemplate.update("INSERT INTO groups_taskusers (group_id, taskuser_id) " +
                "VALUES (" + id + ", ?)", user.getId());
        List<TaskUser> newTaskusers;
        if (group.getTaskusers() == null) {
            newTaskusers = new ArrayList<>();
        } else {
            newTaskusers = group.getTaskusers();
        }
        newTaskusers.add(user);
        group.setTaskusers(newTaskusers);
        return Optional.of(GroupDto.from(group));
    }

    @Override
    public Optional<GroupDto> save(TaskUser newUser) {
        return save(Group.builder().name("maingr").build(), newUser);
    }
}
