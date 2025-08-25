package com.vdt.authservice.entity;

import com.vdt.authservice.constant.Permission;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }


    @Column(unique = true, nullable = false)
    String email;
    String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    Set<Role> roles;

    Boolean isActive = true;


    public String getRoleString() {
        if (roles == null || roles.isEmpty()) {
            return "";
        }

        Set<String> roleValues = new HashSet<>();

        for(Role role: roles){
            for(Permission permission: role.getPermissions()){
                var temp = role.getName() + "_" + permission.name();
                roleValues.add(temp);
            }
        }

        return StringUtils.collectionToDelimitedString(roleValues, " ");
    }


}
