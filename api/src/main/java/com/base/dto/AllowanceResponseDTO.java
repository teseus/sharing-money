package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AllowanceResponseDTO(@JsonProperty("amount") long amount) { }
