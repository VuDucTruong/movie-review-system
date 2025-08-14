package com.vdt.authservice.entity;


import com.vdt.authservice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    String email;
    String password;

    Set<Role> roles = new HashSet<>();

    Boolean isActive = true;


}
