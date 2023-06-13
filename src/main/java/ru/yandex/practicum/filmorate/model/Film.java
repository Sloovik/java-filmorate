package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDateConstraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Film entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @NotNull
    private int id;
    @NotBlank
    @NotNull
    private String name;
    @Size(max = 200)
    @NotNull
    private String description;
    @ReleaseDateConstraint
    @Past
    private LocalDate releaseDate;
    @Positive
    private Integer duration;

}
