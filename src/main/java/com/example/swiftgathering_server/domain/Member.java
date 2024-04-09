package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String loginPassword;

    @Builder
    Member(String loginId, String loginPassword) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }

    @OneToMany(mappedBy = "youngerMember")
    Set<Friendship> friendshipsWithYoungerMember;

    @OneToMany(mappedBy = "olderMember")
    Set<Friendship> friendshipsWithOlderMember;
}
