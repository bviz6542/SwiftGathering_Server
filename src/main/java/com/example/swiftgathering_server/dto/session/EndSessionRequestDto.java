package com.example.swiftgathering_server.dto.session;


import com.example.swiftgathering_server.domain.FlagLocation;
import lombok.Getter;

import java.util.List;

@Getter
public class EndSessionRequestDto {
    private List<FlagLocation> flagLocations;
}
