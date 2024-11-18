package com.github.katemerek.myChatProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registeredPerson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Необходимо ввести имя пользователя")
    @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов длиной")
    @Column(name = "username")
    private String name;

    @NotBlank(message = "Необходимо ввести пароль")
    @Column(name = "password")
    private String password;

    @Min(value = 1924, message = "Пользователь должен быть моложе 1924 года рождения ")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "role")
    private String role;
}
