package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record StatusResponseDTO(
        @JsonProperty("sharedAt") LocalDateTime sharedAt,
        @JsonProperty("sharedTotal") long sharedTotal,
        @JsonProperty("allocatedSum") long allocatedSum,
        @JsonProperty("receivedInfoDTOs") List<ReceivedInfoDTO> receivedInfoDTOs) { }
