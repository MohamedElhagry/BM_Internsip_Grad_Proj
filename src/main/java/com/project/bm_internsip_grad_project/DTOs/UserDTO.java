package com.project.bm_internsip_grad_project.DTOs;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Long balance;
}
