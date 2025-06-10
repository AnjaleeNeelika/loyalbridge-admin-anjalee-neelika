package com.backend.backend.dto;

import lombok.Data;

@Data
public class UserFlagUpdateRequest {
    private Boolean highRisk;
    private Boolean verified;
}
