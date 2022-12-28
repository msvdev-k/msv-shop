package org.msvdev.ee.shop.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotBlank
    @Column(name = "username", length = 64)
    private String username;


    @NotBlank
    @Column(name = "password", length = 80)
    private String password;


    @Email
    @Column(name = "email", length = 64)
    private String email;


    @Column(name = "phone", length = 16)
    private String phone;


    @ManyToMany
    @JoinTable(name = "users_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

}