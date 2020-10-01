package ru.itis.taskmanager.repositories;

import org.postgresql.util.PGInterval;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.dto.FrothDto;

import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Profile("jdbc")
@Repository
public class FrothRepositoryJdbc implements FrothRepository {

//    ==== QUERIES ====

    // ==== SELECT ====
    //language=sql
    private final static String Q_INSERT_FROTH_FULL =
            "INSERT INTO froth (frothname, group_id) " +
                    "VALUES (?, ?);";

    //language=sql
    private final static String Q_SELECT_ALL_IN_GROUP =
            "SELECT f.frothid, f.frothname, f.capacity, f.duration, g.groupid, g.groupname " +
                    "FROM froth f " +
                    "JOIN groups g on g.groupid = ? " +
                    "JOIN groups_taskusers gt on g.groupid = gt.group_id WHERE gt.taskuser_id = ? ;";

    //language=sql
    private final static String Q_FIND_MAIN_GROUP_FOR_FROTH =
            "SELECT DISTINCT g.groupid FROM groups g " +
                    "JOIN froth f on g.groupid = f.group_id " +
                    "JOIN groups_taskusers gt on g.groupid = gt.group_id WHERE g.groupname = 'maingr' AND gt.taskuser_id = ? ;";

//    ==== /QUERIES ====


    private final RowMapper<FrothDto> frothRowMapper = (rs, rowNum) -> FrothDto.from(Froth.builder()
            .id(rs.getLong("frothid"))
            .name(rs.getString("frothname"))
            .capacity(rs.getShort("capacity"))
            .duration(Duration.of(((PGInterval) rs.getObject("duration")).getDays(), ChronoUnit.DAYS))
            .group(Group.builder().id(rs.getLong("groupid")).name(rs.getString("groupname")).build())
            .build());


    private final JdbcTemplate jdbcTemplate;

    public FrothRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Froth> save(Froth froth, Long groupid, Long userid) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(Q_INSERT_FROTH_FULL, new String[]{"frothid"});

            stmt.setString(1, froth.getName());
            stmt.setLong(2, groupid);

            return stmt;
        }, holder);
        froth.setId(Objects.requireNonNull(holder.getKey()).longValue());
        Group newGroup = froth.getGroup();
        String name = jdbcTemplate.queryForObject("SELECT DISTINCT groupname FROM groups JOIN froth f on f.group_id = ? ;", new Object[]{groupid}, String.class);
        newGroup.setId(groupid);
        newGroup.setName(name);
        froth.setGroup(newGroup);
        return Optional.of(froth);
    }

    @Override
    public Optional<Froth> save(Froth froth, Long userid) {
        return save(froth, findMainGroup(userid).orElseThrow(
                () -> new IllegalStateException("User was instantiated wrong")
        ), userid);
    }

    @Override
    public Optional<List<FrothDto>> findAllByGroupId(Long groupid, Long userid) {
        return Optional.of(jdbcTemplate.query(Q_SELECT_ALL_IN_GROUP, frothRowMapper, groupid, userid));
    }

    @Override
    public Optional<List<FrothDto>> findAllByGroupId(Long userid) {
        return findAllByGroupId(findMainGroup(userid).orElseThrow(
                () -> new IllegalStateException("User was instantiated wrong")
        ), userid);
    }

    private Optional<Long> findMainGroup(Long userid) {
        return Optional.of(Objects.requireNonNull(jdbcTemplate.queryForObject(
                Q_FIND_MAIN_GROUP_FOR_FROTH,
                new Object[]{userid}, Long.class)));
    }

}
