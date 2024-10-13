package com.isyll.nftstore.Model;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import com.isyll.nftstore.Validator.UniqueEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
@SQLRestriction("deleted_at = NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email is invalid.")
    @NotBlank(message = "The email address is required.")
    @NotNull(message = "The email address cannot be null.")
    @UniqueEmail
    private String email;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "Firstname is required.")
    @NotNull(message = "Firstname cannot be null.")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "Lastname is required.")
    @NotNull(message = "Lastname cannot be null.")
    private String lastname;

    @Column(name = "avatar_url", nullable = true)
    private String avatarUrl;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "Username is required.")
    @NotNull(message = "Username cannot be null.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = ".*[0-9].*", message = "The password must contain at least one number.")
    @Pattern(regexp = ".*[A-Z].*", message = "The password must contain at least one capital letter.")
    @Pattern(regexp = ".*[a-z].*", message = "The password must contain at least one lower-case letter.")
    @Pattern(regexp = ".*[@#$%^&+=].*", message = "Le mot de passe doit contenir au moins un caractère spécial.")
    @Pattern(regexp = "\\S+", message = "The password must not contain spaces.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password cannot be null.")
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
