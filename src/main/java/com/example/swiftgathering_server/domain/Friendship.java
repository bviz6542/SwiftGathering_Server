package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("younger_member_id")
    @JoinColumn(name = "younger_member_id")
    Member youngerMember;

    @ManyToOne
    @MapsId("older_member_id")
    @JoinColumn(name = "older_member_id")
    Member olderMember;
}