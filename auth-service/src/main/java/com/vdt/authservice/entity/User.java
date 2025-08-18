package com.vdt.authservice.entity;


import com.vdt.authservice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import org.springframework.util.StringUtils;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    Boolean isActive = true;


    public String getRoleString() {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return StringUtils.collectionToDelimitedString(roles, " ");
    }


}
