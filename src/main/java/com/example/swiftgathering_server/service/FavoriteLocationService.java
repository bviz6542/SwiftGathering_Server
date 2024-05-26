package com.example.swiftgathering_server.service;

import com.example.swiftgathering_server.domain.FavoriteLocation;
import com.example.swiftgathering_server.domain.Member;
import com.example.swiftgathering_server.dto.FavoriteLocationInputDto;
import com.example.swiftgathering_server.repository.FavoriteLocationRepository;
import com.example.swiftgathering_server.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteLocationService {

    private final FavoriteLocationRepository favoriteLocationRepository;
    private final MemberRepository memberRepository;

    public Long save(FavoriteLocationInputDto favoriteLocationInputDto) {
        Member member = memberRepository.findOne(favoriteLocationInputDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + favoriteLocationInputDto.getMemberId()));
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

    public List<FavoriteLocation> getAllLocationByMemberId(Long id) {
        Member member = memberRepository.findOne(id)
                .orElseThrow(() -> new IllegalArgumentException("No member found with ID: " + id));
        return favoriteLocationRepository.findAllByMember(member);
    }
}
