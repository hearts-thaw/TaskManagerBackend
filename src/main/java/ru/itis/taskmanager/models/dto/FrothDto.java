package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import ru.itis.taskmanager.models.Froth;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Profile("jdbc")
public class FrothDto {
    private Long id;

    private String name;

    private Short capacity;

    private Long daysBeforeFired;

    private GroupForFrothDto group;

    public static FrothDto from(Froth froth) {
        return FrothDto.builder()
                .id(froth.getId())
                .name(froth.getName())
                .capacity(froth.getCapacity())
                .daysBeforeFired(froth.getDuration().toDays())
                .group(GroupForFrothDto.from(froth.getGroup())).build();
    }

    public static List<FrothDto> from(List<Froth> froths) {
        return froths.stream().map(FrothDto::from).collect(Collectors.toList());
    }
}
