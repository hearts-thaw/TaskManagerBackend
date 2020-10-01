package ru.itis.taskmanager.services;

import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.dto.FrothDto;
import ru.itis.taskmanager.security.models.CustomUser;

import java.util.List;

public interface FrothService extends AuthenticatedService<CustomUser> {
    List<FrothDto> getAllFroth(Long groupid);

    List<FrothDto> getAllFroth();

//    FrothDto getMainFroth(Long groupid);

    Froth add(Froth froth, Long groupid);

    Froth add(Froth froth);
}
