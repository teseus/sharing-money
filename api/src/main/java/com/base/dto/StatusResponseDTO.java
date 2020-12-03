package com.base.dto;

import java.time.LocalDateTime;
import java.util.List;

public record StatusResponseDTO(
        LocalDateTime sharedAt, long sharedTotal, List<ReceivedInfoDTO> seceivedInfoDTOs) { }
