package com.base.service;

import com.base.dto.StatusResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusApplicationService {
    private final SharingApplicationService sharingApplicationService;
    private final AccountApplicationService accountApplicationService;
    public StatusResponseDTO getStatus(final String token) {

//        sharingApplicationService.getAccountsBySharing();



        return null;
    }
}
