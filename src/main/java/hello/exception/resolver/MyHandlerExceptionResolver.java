package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {


    /**
     * exception이 넘어오면 처리를 해서 정상적인 modelAndView로 반환해준다.
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver ", ex);
        try {
            if (ex instanceof IllegalArgumentException) {
                // IllegalArgumentException이게 터져서 컨트롤러 밖으로 나오면 400오류로 바꿀거다.
                log.info("IllegalArgumentException resolver to 400");
                // 이게 기존에 있던 예외를 먹고 새로 배출해준다.
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 여기서 빈 값으로 ModelAndView를 리턴해주면 WAS까지 그냥 정상적인 응답으로 리턴이된다.
                return new ModelAndView();
            }

/*            if (ex ...) {
                response.getWriter().println("{json:'json'}");
            }
 */
        } catch (IOException e) {
            log.error("resolver ex !!!! ", e);
        }

        // 여기서 null로 하면 그냥 기존의 예외가 가려던 길로 간다.
        return null;
    }

}
