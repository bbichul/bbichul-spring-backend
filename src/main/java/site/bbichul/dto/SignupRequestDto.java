package site.bbichul.dto;

import lombok.Data;

@Data

public class SignupRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}

