package com.vdt.authservice.enums;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    ;
    String name;


    Role(String name) {
        this.name = name;
    }
}
