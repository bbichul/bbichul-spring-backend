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
    CANT_VERIFY_CALENDAR("캘린더 정보 확인이 불가능합니다."),
    DUPLICATED_CALENDAR_HOLDER("캘린더 소유자가 중복되었습니다.");

    private final String message;
}
