package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

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
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String login;

    private String name;
    @Past
    @NotNull
    private LocalDate birthday;

}
