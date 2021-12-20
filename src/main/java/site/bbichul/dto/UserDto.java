package site.bbichul.dto;

import lombok.Data;

@Data

public class UserDto {
    private String username;
    private String password;
    private boolean studying;
    private Long teamId;
    private boolean status;
}