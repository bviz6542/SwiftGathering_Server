package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FriendRequest;
import com.example.swiftgathering_server.domain.Friendship;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.FriendRequestCreateDto;
import com.example.swiftgathering_server.dto.FriendRequestOutputDto;
import com.example.swiftgathering_server.dto.FriendRequestUpdateDto;
import com.example.swiftgathering_server.repository.FriendRequestRepository;
import com.example.swiftgathering_server.repository.FriendshipRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;

    public Long sendFriendRequest(Long memberId, FriendRequestCreateDto friendRequestCreateDto) {
        Long receiverId = friendRequestCreateDto.getReceiverId();
        Member sender = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        Member receiver = memberRepository.findOne(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + receiverId));
        FriendRequest friendRequest = FriendRequest.builder()
                .senderMember(sender)
                .receiverMember(receiver)
                .requestStatus(FriendRequest.RequestStatus.PENDING)
                .build();
        return friendRequestRepository.save(friendRequest);
    }

    @Transactional(readOnly = true)
    public List<FriendRequestOutputDto> getAllPendingFriendRequests(Long memberId) {
        Member receiver = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        return friendRequestRepository.getAllPendingFriendRequests(receiver)
                .stream()
                .map((friendRequest) -> new FriendRequestOutputDto(friendRequest.getId(), friendRequest.getSenderMember().getId()))
                .collect(Collectors.toList());
    }

    public void updateFriendRequestStatus(Long memberId, FriendRequestUpdateDto friendRequestUpdateDto) {
        Member receiver = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestUpdateDto.getRequestId())
                .orElseThrow(() -> new IllegalArgumentException("No friend request found with ID: " + friendRequestUpdateDto.getRequestId()));
        if (friendRequestUpdateDto.getIsAccepted()) {
            friendRequest.setRequestStatus(true);
            Friendship friendship = Friendship.builder()
                    .senderMember(friendRequest.getSenderMember())
                    .receiverMember(receiver)
                    .build();
            friendshipRepository.save(friendship);
        } else {
            friendRequest.setRequestStatus(false);
        }
        friendRequestRepository.save(friendRequest);
    }
}
