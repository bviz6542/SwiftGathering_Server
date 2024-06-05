package com.example.swiftgathering_server.controller;

import com.example.swiftgathering_server.dto.FavoriteLocationInputDto;
import com.example.swiftgathering_server.dto.FavoriteLocationOutputDto;
import com.example.swiftgathering_server.service.FavoriteLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members/{memberId}/locations")
@RequiredArgsConstructor
public class FavoriteLocationController {

    private final FavoriteLocationService favoriteLocationService;

    @PostMapping
    public ResponseEntity<Void> saveFavoriteLocation(@PathVariable Long memberId, @RequestBody FavoriteLocationInputDto favoriteLocationInputDto) {
        favoriteLocationService.save(memberId, favoriteLocationInputDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteLocationOutputDto>> getAllLocationByMemberId(@PathVariable Long memberId) {
        List<FavoriteLocationOutputDto> favoriteLocations = favoriteLocationService.getAllLocationByMemberId(memberId);
        return ResponseEntity.ok(favoriteLocations);
    }
}