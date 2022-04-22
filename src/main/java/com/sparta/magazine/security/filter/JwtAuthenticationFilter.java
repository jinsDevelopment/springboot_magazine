package com.sparta.magazine.security.filter;

import com.sparta.magazine.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        try {
            if (accessToken != null) {
                // 유효한 토큰인지 확인합니다.
                if (jwtTokenProvider.validateToken(accessToken)) {
                    //유효한 토큰이면
                    this.setAuthentication(accessToken);
                }else {
                    request.setAttribute("exception", "WRONG_TYPE_TOKEN");
                }
            }else {
                request.setAttribute("exception", "NO_TOKEN");
            }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", "WRONG_TYPE_TOKEN");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "EXPIRED_TOKEN");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UNSUPPORTED_TOKEN");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "WRONG_TOKEN");
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", accessToken);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", "UNKNOWN_ERROR");
        }

//        if (accessToken != null) {
//            // 유효한 토큰인지 확인합니다.
//            if (jwtTokenProvider.validateToken(accessToken)) {
//                //유효한 토큰이면
//                this.setAuthentication(accessToken);
//            } else {
//                // 필터를 거치면서 로그인 정보를 null로 만들어버려서 로그인 안한상태로 판단하도록
////                SecurityContextHolder.getContext().setAuthentication(null);
//
//            }
//        } else {
//            // 필터를 거치면서 로그인 정보를 null로 만들어버려서 로그인 안한상태로 판단하도록
////            SecurityContextHolder.getContext().setAuthentication(null);
//        }
        chain.doFilter(request, response);
    }

    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
