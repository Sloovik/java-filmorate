package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * User entity
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    private int id;
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
