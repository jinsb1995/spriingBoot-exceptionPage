package hello.exception.api;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionV3Controller {

    @GetMapping("/api3/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            // 1. 얘가 터지면 dispatcherServlet에 갔다가 ExceptionResolver로 가서 예외 해결 시도를 한다.
            // 2. 그럼 ExceptionResolver 해결방법 우선순위에 따라 ExceptionHandlerExceptionResolver로 간다.
            // 3. 그럼 ExceptionHandlerExceptionResolver가 controller에 @ExceptionHandler가 있는지 찾아본다.
            // 4. 찾아봤을 때 @ExceptionHandler가 있으면 그걸 호출해준다.
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello" + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
