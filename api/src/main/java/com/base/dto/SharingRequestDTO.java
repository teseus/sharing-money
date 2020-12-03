package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SharingRequestDTO(@JsonProperty("sharingMoney") long sharingMoney,
                                @JsonProperty("targetSize") long targetSize) { }
