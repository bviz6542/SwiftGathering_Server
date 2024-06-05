package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FavoriteLocation;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.FavoriteLocationInputDto;
import com.example.swiftgathering_server.dto.FavoriteLocationOutputDto;
import com.example.swiftgathering_server.repository.FavoriteLocationRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteLocationService {

    private final FavoriteLocationRepository favoriteLocationRepository;
    private final MemberRepository memberRepository;

    public Long save(Long memberId, FavoriteLocationInputDto favoriteLocationInputDto) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        FavoriteLocation favoriteLocation = FavoriteLocation
                .builder()
                .member(member)
                .latitude(favoriteLocationInputDto.getLatitude())
                .longtitude(favoriteLocationInputDto.getLongtitude())
                .name(favoriteLocationInputDto.getName())
                .description(favoriteLocationInputDto.getDescription())
                .build();
        favoriteLocationRepository.save(favoriteLocation);
        return favoriteLocation.getId();
    }

    public List<FavoriteLocationOutputDto> getAllLocationByMemberId(Long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + memberId));
        return favoriteLocationRepository.findAllByMember(member).stream()
                .map(location -> new FavoriteLocationOutputDto(
                        location.getId(),
                        location.getLatitude(),
                        location.getLongtitude(),
                        location.getName(),
                        location.getDescription()))
                .collect(Collectors.toList());
    }
}
