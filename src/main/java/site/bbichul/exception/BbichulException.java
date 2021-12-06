package site.bbichul.exception;

import lombok.Getter;

@Getter
public class BbichulException extends RuntimeException{
    private BbichulErrorCode bbichulErrorCode;
    private String detailMessage;

    public BbichulException(BbichulErrorCode errorCode) {
        super(errorCode.getMessage());
        this.bbichulErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

}
