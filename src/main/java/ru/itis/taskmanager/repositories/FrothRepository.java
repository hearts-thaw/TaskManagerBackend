package ru.itis.taskmanager.repositories;

import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.dto.FrothDto;

import java.util.List;
import java.util.Optional;

public interface FrothRepository {
    Optional<Froth> save(Froth froth, Long groupid, Long userid);

    Optional<Froth> save(Froth froth, Long userid);

    Optional<List<FrothDto>> findAllByGroupId(Long groupid, Long userid);

    Optional<List<FrothDto>> findAllByGroupId(Long userid);
}
