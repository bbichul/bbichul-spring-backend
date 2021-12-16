package site.bbichul.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BbichulErrorCode {
    NOT_FOUND_MEMO("메모를 찾지 못했습니다."),
    NOT_FOUND_USER("유저를 찾지 못했습니다."),
    NOT_FOUND_MATCHED_CALENDAR("해당 정보로 캘린더를 찾지 못했습니다."),
    CANT_VERIFY_USER("유저 정보 확인이 불가능합니다."),
    CANT_VERIFY_TEAM("팀 정보 확인이 불가능합니다."),
    CANT_VERIFY_CALENDAR("캘린더 정보 확인이 불가능합니다."),
    DUPLICATED_CALENDAR_HOLDER("캘린더 소유자가 중복되었습니다."),
    NOT_CHECKED_PRIVATE_USER_CALENDAR("유저 캘린더에 Private 체크가 되지 않았습니다."),
    CHECKED_PRIVATE_TEAM_CALENDAR("팀 캘린더에 Private 체크가 되어 있습니다."),
    CANT_INPUT_VACUUM("공백 입력은 불가능 합니다."),
    BROKEN_FORMAT_DATEDATA("날짜 형식이 맞지 않습니다.");

    private final String message;
}
