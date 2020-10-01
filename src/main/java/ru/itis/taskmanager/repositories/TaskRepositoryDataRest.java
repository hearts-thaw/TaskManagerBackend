package ru.itis.taskmanager.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itis.taskmanager.models.Task;

import java.util.List;

@Profile("jpa")
@RepositoryRestResource
public interface TaskRepositoryDataRest extends Repository<Task, Long> {
//    @Modifying
//    @Query(
//            value = "INSERT INTO tasks (tasktext, user_id) " +
//                            "VALUES ( :#{task.text}, :#{principal.getId()} ) ;",
//            nativeQuery = true
//    )
//    Integer save(Task task);

    @Query("SELECT t FROM Task AS t WHERE t.user_id.id = :userid AND t.completed = :completed")
    List<Task> findAllTasksByUserIdAndCompletion(Long userid, Boolean completed);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.user_id.id = :userid AND t.id = :id")
    int delete(Long id, Long userid);
}
