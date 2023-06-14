package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * User entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull
    private int id;
    @Email
    @NotNull
    private String email;
    @NotBlank
    @Pattern(regexp = "\\A(?!\\s*\\Z).+")
    private String login;

    private String name;
    @Past
    @NotNull
    private LocalDate birthday;

}
