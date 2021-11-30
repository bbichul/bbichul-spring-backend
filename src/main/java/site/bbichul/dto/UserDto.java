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
    private boolean isstudying;
    private Long team_id;
}