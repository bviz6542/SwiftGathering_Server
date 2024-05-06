package com.example.swiftgathering_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    private String loginPassword;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder
    Member(String loginId, String loginPassword, String name) {
        this.loginId = loginId;
        this.loginPassword = loginPassword;
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "youngerMember")
    Set<Friendship> friendshipsWithYoungerMember;

    @JsonIgnore
    @OneToMany(mappedBy = "olderMember")
    Set<Friendship> friendshipsWithOlderMember;
}
