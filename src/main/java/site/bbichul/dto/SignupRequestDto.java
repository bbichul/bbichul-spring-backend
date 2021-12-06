package site.bbichul.dto;

import lombok.Getter;
import lombok.Setter;
import site.bbichul.models.UserInfo;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}

