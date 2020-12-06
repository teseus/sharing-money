package com.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReceivedInfoDTO(@JsonProperty("userId")long userId,
                              @JsonProperty("receivedAmount")long allocatedAmount) { }
