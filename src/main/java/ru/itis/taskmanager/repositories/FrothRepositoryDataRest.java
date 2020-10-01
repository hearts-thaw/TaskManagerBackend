package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.projections.FrothProj;

import java.util.Optional;
import java.util.Set;

@Profile("jpa")
@RepositoryRestResource(excerptProjection = FrothProj.class)
public interface FrothRepositoryDataRest extends JpaRepository<Froth, Long> {
    @Query("SELECT f FROM Froth f JOIN f.group g WHERE g.id = :groupid")
    Optional<Set<Froth>> findAllByGroupId(Long groupid);
}
