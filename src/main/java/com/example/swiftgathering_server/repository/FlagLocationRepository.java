package com.example.swiftgathering_server.repository;

import com.example.swiftgathering_server.domain.FlagLocation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FlagLocationRepository {

    private final EntityManager em;

    public Long save(FlagLocation flagLocation) {
        em.persist(flagLocation);
        return flagLocation.getId();
    }

    public Optional<List<Long>> saveAll(List<FlagLocation> flagLocations) {
        flagLocations.forEach(em::persist);
        List<Long> ids = flagLocations.stream()
                .map(FlagLocation::getId)
                .collect(Collectors.toList());
        return Optional.of(ids);
    }
}