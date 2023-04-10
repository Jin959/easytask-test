package com.easytask.easytask.common.jwt;
import com.easytask.easytask.common.exception.BaseException;
import com.easytask.easytask.common.response.BaseResponseStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 에러 호출
        throw new BaseException(BaseResponseStatus.INVALID_TOKEN);
    }
}