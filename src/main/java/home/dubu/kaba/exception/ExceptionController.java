package home.dubu.kaba.exception;

import home.dubu.kaba.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(KabaException.class)
    public ResponseEntity<ErrorResponse> handleWebClientRequestException(KabaException e) {
        System.out.println(e.getMessage());
        return ErrorResponse.toResponseEntity(e.getResultCode());
    }


    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    public ResponseEntity<ErrorResponse> handleWebClientRequestException(WebClientResponseException e) {
        System.out.println(e.getMessage());
        return ErrorResponse.toResponseEntity(ResultCode.CLIENT_INVALID_REQUEST);
    }


    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleWebClientInternalServerException(WebClientResponseException e) {
        System.out.println(e.getMessage());
        return ErrorResponse.toResponseEntity(ResultCode.CLIENT_INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException e) {
        System.out.println(e.getMessage());
        return ErrorResponse.toResponseEntity(ResultCode.INVALID_PARAMETER);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        System.out.println(e.getMessage());
        return ErrorResponse.toResponseEntity(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
