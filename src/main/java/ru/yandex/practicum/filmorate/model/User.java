package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * User entity
 */
@Data
@Builder(toBuilder = true)
public class User {
    @NotNull
    private final int id;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @Pattern(regexp = "\\S+")
    private String login;
    private String name;
    @PastOrPresent
    @NotNull
    private LocalDate birthday;

}
