package ru.itis.taskmanager.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> findById(Long id, Long userid);

    Optional<List<T>> findAllByIds(List<Long> ids, Long userid);

    int delete(Long id, Long userid);
}
