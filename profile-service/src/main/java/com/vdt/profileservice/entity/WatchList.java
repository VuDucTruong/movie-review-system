package com.vdt.profileservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime createdAt;

    Long movieId;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false, referencedColumnName = "user_id")
    Profile profile;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
