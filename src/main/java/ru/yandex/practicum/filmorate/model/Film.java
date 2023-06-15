package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film entity
 */
@Data
@Builder(toBuilder = true)
public class Film {
    private final int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotNull
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private Integer duration;

}
