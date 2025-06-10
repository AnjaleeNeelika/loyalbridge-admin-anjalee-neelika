package com.backend.backend.dto;

import com.backend.backend.entities.UserStatus;
import lombok.Data;

@Data
public class UserStateUpdateRequest {
    private UserStatus status;
}
