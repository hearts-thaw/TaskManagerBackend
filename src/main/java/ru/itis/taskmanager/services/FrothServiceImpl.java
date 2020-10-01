package ru.itis.taskmanager.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.dto.FrothDto;
import ru.itis.taskmanager.repositories.FrothRepository;

import java.util.List;

@Profile("jdbc")
@Service
@Transactional
public class FrothServiceImpl implements FrothService {

    private final FrothRepository frothRepository;

    public FrothServiceImpl(FrothRepository frothRepository) {
        this.frothRepository = frothRepository;
    }

    @Override
    public List<FrothDto> getAllFroth(Long groupid) {
        return frothRepository.findAllByGroupId(groupid, getId()).orElseThrow(
                () -> new IllegalArgumentException("Wrong group id")
        );
    }

    @Override
    public List<FrothDto> getAllFroth() {
        return frothRepository.findAllByGroupId(getId()).orElseThrow(
                () -> new IllegalStateException("User was instantiated wrong")
        );
    }

//    @Override
//    public FrothDto getMainFroth(Long groupid) {
//        return frothRepository.findDefaultByGroup_id(groupid, getId()).orElseThrow(
//                () -> new IllegalStateException("User instantiated wrong")
//        );
//    }

    @Override
    public Froth add(Froth froth, Long groupid) {
        return frothRepository.save(froth, groupid, getId()).orElseThrow(
                () -> new IllegalArgumentException("No such group id")
        );
    }

    @Override
    public Froth add(Froth froth) {
        return frothRepository.save(froth, getId()).orElseThrow(
                () -> new IllegalStateException("User is instantiated bad")
        );
    }
}
