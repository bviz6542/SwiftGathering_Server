package com.example.swiftgathering_server.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Member senderMember;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiverMember;

    private RequestStatus requestStatus;

    @Builder
    FriendRequest(Member senderMember, Member receiverMember, RequestStatus requestStatus) {
        this.senderMember = senderMember;
        this.receiverMember = receiverMember;
        this.requestStatus = requestStatus;
    }

    public enum RequestStatus {
        PENDING, ACCEPTED, DECLINED
    }

    public void setRequestStatus(Boolean isAccepted) {
        if (isAccepted) {
            requestStatus = RequestStatus.ACCEPTED;
        } else {
            requestStatus = RequestStatus.DECLINED;
        }
    }
}