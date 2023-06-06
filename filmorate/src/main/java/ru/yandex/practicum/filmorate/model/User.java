package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class User {

    @NotNull
    private Long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @Past
    private LocalDate birthday;

}
