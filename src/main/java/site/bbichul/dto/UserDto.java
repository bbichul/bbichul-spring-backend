package site.bbichul.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class UserDto {
    private String username;
    private String password;
    private boolean isStudying;
    private Long teamId;
    private boolean status;
}