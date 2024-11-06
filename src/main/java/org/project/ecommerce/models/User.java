package org.project.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="login")
    @NotBlank(message = "Username cannot be empty")
    @Size(min=6, max=20)
    private String login;

    @Column(name="password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min=8, max=25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
