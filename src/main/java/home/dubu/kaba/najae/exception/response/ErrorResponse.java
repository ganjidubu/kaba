package home.dubu.kaba.najae.exception.response;

import home.dubu.kaba.najae.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResultCode resultCode) {
        return ResponseEntity.status(resultCode.getHttpStatus())
                             .body(ErrorResponse.builder()
                                                .message(resultCode.getMessage())
                                                .timestamp(LocalDateTime.now())
                                                .build());
    }
}
