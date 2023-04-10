package com.easytask.easytask.common.jwt;
import com.easytask.easytask.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    public JwtFilter(TokenProvider tokenProvider ,RedisUtil redisUtil) {
        this.tokenProvider = tokenProvider;
        this.redisUtil=redisUtil;
    }

    @Override //토큰의 인증정보를 SecurityContext에 저장하는 역할
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;


        String jwt = resolveToken(httpServletRequest); //resolveToken 을 통해 토큰을 받아옴
        String requestURI = httpServletRequest.getRequestURI();


        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { //받아욘 토큰을 아까 만든 tokenProvider 에서 유효성 검증을 한다.
            if(ObjectUtils.isEmpty(redisUtil.getData(jwt))){ //레디스에 토큰값이 올라가 있는지 확인(로그아웃 하면 레디스에 토큰값이 들어감)
                Authentication authentication = tokenProvider.getAuthentication(jwt); //토큰이 정상이면 Authentication 객체를 받아온다.
                SecurityContextHolder.getContext().setAuthentication(authentication); //받아온 Authentication 객체를 SecurityContext에 set 해준다, 즉 저장한다
                logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //Request Header 에서 토큰 정보를 꺼내오는 역할
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}