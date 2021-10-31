package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad") // 400
public class BadRequestException extends RuntimeException {
    // 원래 이게 터지면 500이 나와야한다.
    // userException, handler등 구현했던 것들을 공통으로 잡아주는 역할을 한다.

    // 여기가 터지면 ExceptionResolver가 한번 살펴본다.
    // 어떤 리졸버냐면 ResponseStatusExceptionResolver에 걸린다.





}
