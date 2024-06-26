package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "friendship", uniqueConstraints = { @UniqueConstraint(columnNames = { "sender", "receiver" }) })
public class Friendship {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    Member senderMember;

    @ManyToOne
    @JoinColumn(name = "receiver", nullable = false)
    Member receiverMember;

    @Builder
    Friendship(Member senderMember, Member receiverMember) {
        this.senderMember = senderMember;
        this.receiverMember = receiverMember;
    }
}