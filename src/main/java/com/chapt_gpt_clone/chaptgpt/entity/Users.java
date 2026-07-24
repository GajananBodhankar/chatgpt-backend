package com.chapt_gpt_clone.chaptgpt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends  Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String first_name;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String last_name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Email verification field is required")
    @Column(nullable = false)
    private boolean emailVerified=false;

    public boolean getEmailVerified(){
        return emailVerified;
    }

    public Users(String first_name, String last_name, String email, boolean emailVerified) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.emailVerified = emailVerified;
    }
}
