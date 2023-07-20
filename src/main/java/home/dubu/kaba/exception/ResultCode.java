package home.dubu.kaba.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // client
    CLIENT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    CLIENT_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API와의 연동에 실패했습니다."),

    // application
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "시스템 오류가 발생했습니다.");

    private HttpStatus httpStatus;
    private String message;
}
