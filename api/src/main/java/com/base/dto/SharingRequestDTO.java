package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public record SharingRequestDTO(@JsonProperty("totalMoney")
                                @Min(value = 1L) long totalMoney,
                                @JsonProperty("separatedSize")
                                @Min(value = 1L) long separatedSize) { }
