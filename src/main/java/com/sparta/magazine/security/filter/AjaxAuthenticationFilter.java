package com.sparta.magazine.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.magazine.model.User;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final ObjectMapper objectMapper;

	public AjaxAuthenticationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper) {
		super(requestMatcher);
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isJson(request)) {
			User user = objectMapper.readValue(request.getReader(), User.class);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
			return getAuthenticationManager().authenticate(authentication);
		} else {
			throw new AccessDeniedException("Don't use content type for " + request.getContentType());
		}
	}

	private boolean isJson(HttpServletRequest request) {
		return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType());
	}
}
