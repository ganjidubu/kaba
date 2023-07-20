package home.dubu.kaba.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KabaException extends RuntimeException {
    private final ResultCode resultCode;
}
