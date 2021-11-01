package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")
public class ExControllerAdvice {


//    // 아래에서 터지면 얘가 잡아준다.
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalArgument(IllegalArgumentException e) {
//        log.error("[execptionHandler] ex = ", e);
//        // 5. 그럼 여기서 정상 흐름으로 바꿔서 정상적으로 리턴을 해준다.
//        // 6. 문제는 정상 흐름 처리라서 200으로 가게되는데
//        return new ErrorResult("BAD", e.getMessage());
//    }

    // 7. 여기서 @ResponseStatus로 응답 상태를 바꿔주면 된다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgument(IllegalArgumentException e) {
        log.error("[exceptionHandler illegalArgument] ex = ", e);
        return new ErrorResult("BAD", e.getMessage());
    }


    // 예제 2번
    @ExceptionHandler  // 파라미터 생략 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler userExHandler] ex == ", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 예제 3번
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        // 위에서 놓친 애들을 여기서 처리하기 위해 공통 처리 exception을 만들었다.
        log.error("[exceptionHandler Exception", e);
        return new ErrorResult("EX", "내부 오류");
    }

}