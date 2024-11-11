package com.qrmenu.UserService.domain.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserIdentity implements Serializable {
    private UUID userId;
    private String username;
    private String role;
}
