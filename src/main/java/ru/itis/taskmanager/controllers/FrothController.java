package ru.itis.taskmanager.controllers;

import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.dto.FrothDto;
import ru.itis.taskmanager.services.FrothService;

import javax.validation.Valid;
import java.util.List;

@Profile("jdbc")
@RestController
@RequestMapping(value = "/groups/{groupid}/froth")
public class FrothController {
    private final FrothService frothService;

    public FrothController(FrothService frothService) {
        this.frothService = frothService;
    }

    @GetMapping
    public ResponseEntity<List<FrothDto>> showFrothList(@ApiParam(allowEmptyValue = true) @PathVariable(required = false) Long groupid) {
        List<FrothDto> res;
        if (groupid != null) {
            res = frothService.getAllFroth(groupid);
        } else {
            res = frothService.getAllFroth();
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<FrothDto> addFroth(@RequestBody @Valid Froth froth,
                                             @PathVariable(required = false) Long groupid) {
        Froth res;
        if (groupid != null) {
            res = frothService.add(froth, groupid);
        } else {
            res = frothService.add(froth);
        }
        return ResponseEntity.ok(FrothDto.from(res));
    }

}
