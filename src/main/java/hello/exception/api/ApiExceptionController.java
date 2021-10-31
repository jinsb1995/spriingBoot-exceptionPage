package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            // 여기서 예외가 터지면 servletContainer까지 오류가 올라간다. (스프링 부트가 기본 오류 페이지 /error를 등록한 것을 찾는다.)
            // servletContainer 까지 올라가면 /error를 호출하게 되는데 그렇게 되면 BasicErrorController 로 들어가게 된다.
            // BasicErrorController 가 JSON 형식의 어떤 데이터를 클라이언트에게 내려준다.
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        // 얘도 ResponseSstatusExceptionResolver가 처리해준다.
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
