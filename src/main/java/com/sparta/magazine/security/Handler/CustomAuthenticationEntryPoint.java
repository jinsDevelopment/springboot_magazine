package com.sparta.magazine.security.Handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	protected static final Log LOG = LogFactory.getLog(CustomAuthenticationEntryPoint.class);
	@SuppressWarnings("unchecked")
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		String exception = (String)request.getAttribute("exception");
		System.out.println("exception :: " + exception);
		if(exception != null){
			setResponse(response);
		}


		// 권한이 없을 경우 알려주는.. 401 에러
	}

	//한글 출력을 위해 getWriter() 사용
	private void setResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		JSONObject responseJson = new JSONObject();
		responseJson.put("message", "로그인 정보가 없습니다.");
		responseJson.put("code", HttpStatus.UNAUTHORIZED);

		response.getWriter().print(responseJson);
	}
}
