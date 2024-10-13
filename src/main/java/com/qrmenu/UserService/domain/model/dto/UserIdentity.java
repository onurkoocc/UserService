package com.qrmenu.UserService.domain.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserIdentity {
    private UUID userId;
    private String username;
    private String role;
}
