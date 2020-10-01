package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.Group;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository
@RepositoryRestResource
public interface GroupRepositoryDataRest extends JpaRepository<Group, Long> {
    Optional<List<Group>> findByTaskusers_Id(Long taskusers_id);

    @Query("SELECT g.id FROM Group g JOIN TaskUser tu WHERE tu.id = :taskusers_id AND g.name = 'maingr'")
    Optional<Long> findMainGroupIdByTaskusers_Id(Long taskusers_id);
}
