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

    @EmbeddedId
    private FriendshipId id;

    @ManyToOne
    @MapsId("youngerMemberId")
    @JoinColumn(name = "younger_member_id")
    Member youngerMember;

    @ManyToOne
    @MapsId("olderMemberId")
    @JoinColumn(name = "older_member_id")
    Member olderMember;

    @Builder
    Friendship(Member youngerMember, Member olderMember) {
        this.id = new FriendshipId(youngerMember.getId(), olderMember.getId());
        this.youngerMember = youngerMember;
        this.olderMember = olderMember;
    }
}