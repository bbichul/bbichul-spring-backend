package site.bbichul.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BbichulErrorCode {
    NOT_FOUND_MEMO("메모를 찾지 못했습니다."),
    NOT_FOUND_USER("유저를 찾지 못했습니다.");

    private final String message;
}
