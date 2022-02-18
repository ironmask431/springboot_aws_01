package com.jojodu.book.springboot.config.auth;

import com.jojodu.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

/**
 * HandlerMethodArgumentResolver 기능 지원 :
 * 조건에 맞는 메소드가 있다면 HandlerMethodArgumentResolver 구현체가 지정한 값을
 * 해당 메소드의 파라미터로 넘길 수 있습니다.
 */

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    //컨트롤러 메서드가 특정 파라미터를 지원하는지 판단합니다.
    @Override
    public boolean supportsParameter(MethodParameter parameter){
        boolean isLoginUserAnnotation =
                parameter.getParameterAnnotation(LoginUser.class) != null;
                //파라미터에 @LoginUser 어노테이션이 붙어 있는지 여부
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        //파라미터 클래스타입이 SessioUser 인지 여부
        return isLoginUserAnnotation && isUserClass;
    }

    //파라미터에 전달할 객체를 생성합니다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
    , NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
        return httpSession.getAttribute("user");
        //세션의 user 정보를 리턴합니다.
    }


}
