package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDTO(@JsonProperty("token") String token) {
}
