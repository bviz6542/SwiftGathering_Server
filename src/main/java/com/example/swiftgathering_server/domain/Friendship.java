package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "FRIENDSHIP")
public class Friendship {

    @Id
    @GeneratedValue
    @Column(name = "friendship_id")
    private Long id;

    private Long earlierUserId;
    private Long laterUserId;
}