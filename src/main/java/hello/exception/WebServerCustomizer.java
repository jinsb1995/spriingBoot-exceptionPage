package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

// 이거 주석 처리하면 ErrorPage가 자동으로 등록되어서 모든 오류는 /error를 호출하게 된다.
// ErrorMvcAutoConfiguration이라는 클래스가 오류 페이지를 자동으로 등록하는 역할을 한다.
// basicErrorController는 /error를 처리하기 위한 컨트롤러이다.
//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // 오류가 발생해서 여기(was)까지 왔다가 경로를 확인한 후 view를 찾기 위해 다시 내부로 들어간다.
        // runtimeException이 발생해서 500에러가 잡힐텐데 was까지 가다가 여기에서 잡히면
        // path를 통해 맞는 url로 이동한다.
        // 그럼 그 url이 있는 controller를 찾아가서 view를 찾아서 반환해준다.
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }


}
