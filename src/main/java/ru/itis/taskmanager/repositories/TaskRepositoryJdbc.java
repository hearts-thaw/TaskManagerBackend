package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.models.TaskUser;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("jdbc")
public class TaskRepositoryJdbc implements TaskRepository {
//    ==== QUERIES ====

    // ==== SELECT ====

    //    language=sql
    private static final String Q_SELECT_ONE_BY_ID =
            "SELECT * FROM tasks t " +
                    "WHERE t.taskid = ? AND t.user_id = ? ;";

    //    language=sql
    private static final String Q_SELECT_BY_FROTH_ID_AND_USER_ID_AND_COMPLETED_AND_ONFIRE =
            "SELECT t.* FROM tasks t " +
                    "WHERE t.froth_id = ? AND t.user_id = ? AND t.completed = ? AND t.onfire = ? ;";

    //    language=sql
    private static final String Q_SELECT_BY_FROTH_ID_AND_USER_ID =
            "SELECT t.* FROM tasks t " +
                    "WHERE t.froth_id = ? AND t.user_id = ? ;";

    //    language=sql
    private static final String Q_SELECT_BY_USER_ID_AND_COMPLETED_AND_ONFIRE =
            "SELECT t.* FROM tasks t " +
                    "WHERE t.user_id = ? AND t.completed = ? AND t.onfire = ? ;";

    //    language=sql
    private static final String Q_SELECT_BY_USER_ID =
            "SELECT t.* FROM tasks t " +
                    "WHERE t.user_id = ? ;";

    // ==== INSERT ====

    //    language=sql
    private static final String Q_INSERT_TASK =
            "INSERT INTO tasks ( tasktext, datetime, completed, onfire, froth_id, user_id ) " +
                    "VALUES (?, ?, ?, ?, ?, ?) ;";

    // ==== UPDATE ====

    //    language=sql
    private static final String Q_UPDATE_TASK_TEXT =
            "UPDATE tasks SET tasktext = ? WHERE user_id = ? AND taskid = ? ;";

    //    language=sql
    private static final String Q_UPDATE_TASK_COMPLETION =
            "UPDATE tasks SET completed = '1' WHERE user_id = ? AND taskid = ? ;";

    // ==== DELETE ====

    //    language=sql
    private static final String Q_DELETE_TASK_BY_ID =
            "DELETE FROM tasks WHERE taskid = ? AND user_id = ? ;";


//  ==== /QUERIES ====


    private final RowMapper<Task> taskWithFrothAndUserRowMapper = (rs, rowNum) -> Task.builder()
            .id(rs.getLong("taskid"))
            .text(rs.getString("tasktext"))
            .completed(rs.getBoolean("completed"))
            .datetime(((Timestamp) rs.getObject("datetime")).toLocalDateTime())
            .onfire(rs.getBoolean("onfire"))
            .froth_id(Froth.builder()
                    .id(rs.getLong("froth_id"))
                    .build())
            .user_id(TaskUser.builder()
                    .id(rs.getLong("user_id"))
                    .build()).build();

//    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> Task.builder()
//            .id(rs.getLong("taskid"))
//            .text(rs.getString("tasktext"))
//            .completed(rs.getBoolean("completed"))
//            .datetime(((Timestamp) rs.getObject("datetime")).toLocalDateTime())
//            .onfire(rs.getBoolean("onfire"))
//            .user_id(TaskUser.builder().id(rs.getLong("user_id" +
//                    "")).build()).build();


    private final JdbcTemplate jdbcTemplate;

    public TaskRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<Task>> findAllTasksByCompletedAndOnfireAndFroth_Id(Boolean completed, Boolean onfire,
                                                                            Long frothid, Long userid) {
        return Optional.of(jdbcTemplate.query(Q_SELECT_BY_FROTH_ID_AND_USER_ID_AND_COMPLETED_AND_ONFIRE,
                taskWithFrothAndUserRowMapper,
                frothid, userid, completed, onfire));
    }

    @Override
    public Optional<List<Task>> findAll(Long frothid, Long userid) {
        return Optional.of(jdbcTemplate.query(Q_SELECT_BY_FROTH_ID_AND_USER_ID,
                taskWithFrothAndUserRowMapper, frothid, userid));
    }

    @Override
    public Optional<Task> save(Task task, Long frothid, Long userid) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(Q_INSERT_TASK, new String[]{"taskid"});

            stmt.setString(1, task.getText());
            stmt.setObject(2, task.getDatetime());
            stmt.setBoolean(3, task.isCompleted());
            stmt.setBoolean(4, task.isOnfire());
            stmt.setLong(5, frothid);
            stmt.setLong(6, userid);

            return stmt;
        }, holder);
        task.setId(Objects.requireNonNull(holder.getKey()).longValue());
        Froth newFroth = task.getFroth_id();
        String name = jdbcTemplate.queryForObject("SELECT DISTINCT frothname FROM froth f " +
                        "JOIN taskusers tu on tu.userid = ? WHERE f.frothid = ? ;",
                new Object[]{userid, frothid}, String.class);
        newFroth.setId(frothid);
        newFroth.setName(name);
        task.setFroth_id(newFroth);
        return Optional.of(task);
    }

    @Override
    public Optional<Task> updateCompleted(Long id, Long userid) {
        jdbcTemplate.update(Q_UPDATE_TASK_COMPLETION, userid, id);
        return findById(id, userid);
    }

    @Override
    public Optional<Task> updateEdited(Long id, String text, Long userid) {
        jdbcTemplate.update(Q_UPDATE_TASK_TEXT, text, userid, id);
        return findById(id, userid);
    }

    @Override
    public Optional<Task> findById(Long id, Long userid) {
        return Optional.of(Objects.requireNonNull(jdbcTemplate.queryForObject(Q_SELECT_ONE_BY_ID,
                taskWithFrothAndUserRowMapper, id, userid)));
    }

    @Override
    public Optional<List<Task>> findAllByIds(List<Long> ids, Long userid) {
        return Optional.of(ids.stream().map(id -> findById(id, userid).orElseThrow(
                () -> new IllegalArgumentException("No such id: " + id)
        )).collect(Collectors.toList()));
    }

    @Override
    public int delete(Long id, Long userid) {
        return jdbcTemplate.update(Q_DELETE_TASK_BY_ID, id, userid);
    }
}
