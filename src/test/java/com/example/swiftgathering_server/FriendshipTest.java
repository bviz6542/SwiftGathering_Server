package com.example.swiftgathering_server;

import com.example.swiftgathering_server.dto.FriendInfoDto;
import com.example.swiftgathering_server.dto.FriendRequestCreateDto;
import com.example.swiftgathering_server.dto.FriendRequestUpdateDto;
import com.example.swiftgathering_server.dto.RegisterDto;
import com.example.swiftgathering_server.service.FriendRequestService;
import com.example.swiftgathering_server.service.FriendshipService;
import com.example.swiftgathering_server.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class FriendshipTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private EntityManager em;

    @Test
    public void 친구목록() {
        // given
        RegisterDto registerDto0 = new RegisterDto("member0", "asdasdf", "mem0");
        Long member0Id = memberService.register(registerDto0);
        RegisterDto registerDto1 = new RegisterDto("member1", "asdasdf", "mem1");
        Long member1Id = memberService.register(registerDto1);
        RegisterDto registerDto2 = new RegisterDto("member2", "asdasdf", "mem2");
        Long member2Id = memberService.register(registerDto2);
        em.flush();
 
        // when
        FriendRequestCreateDto friendRequestCreateDto0to1 = new FriendRequestCreateDto(member1Id);
        Long request0to1Id = friendRequestService.sendFriendRequest(member0Id, friendRequestCreateDto0to1);
        FriendRequestCreateDto friendRequestCreateDto0to2 = new FriendRequestCreateDto(member2Id);
        Long request0to2Id = friendRequestService.sendFriendRequest(member0Id, friendRequestCreateDto0to2);

        FriendRequestUpdateDto friendRequestUpdateDto1to0 = new FriendRequestUpdateDto(request0to1Id, true);
        friendRequestService.updateFriendRequestStatus(member1Id, friendRequestUpdateDto1to0);
        FriendRequestUpdateDto friendRequestUpdateDto2to0 = new FriendRequestUpdateDto(request0to2Id, false);
        friendRequestService.updateFriendRequestStatus(member2Id, friendRequestUpdateDto2to0);

        // then
        List<Long> resultIds = friendshipService.findAllFriendsOfUser(member0Id)
                .stream().map(FriendInfoDto::getId).collect(Collectors.toList());
        List<Long> expectedIds = Arrays.asList(new Long[]{member1Id});
        Assertions.assertEquals(expectedIds, resultIds);
    }  
}
