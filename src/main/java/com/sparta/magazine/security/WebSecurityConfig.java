package com.sparta.magazine.security;

import com.sparta.magazine.security.Handler.CustomAuthenticationEntryPoint;
import com.sparta.magazine.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // 스프링 Security 필터가 스프링 필터 체인에 등록됨.
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CorsFilter corsFilter;

	public WebSecurityConfig(JwtTokenProvider jwtTokenProvider,
		CorsFilter corsFilter) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.corsFilter = corsFilter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// 비밀번호 암호화 Bean 등록
	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(){
		return new CustomAuthenticationEntryPoint();
	}

	@Override
	public void configure(WebSecurity web) {
	// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
		web
			.ignoring()
			.antMatchers("/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
			//요청에 의한 보안검사 시작
			.authorizeRequests()
			// 회원 관리 처리 API 전부를 login 없이 허용
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/api/user/**").permitAll()
			// Get 요청 login 없이 허용
			.antMatchers(HttpMethod.GET, "/api/post/**").permitAll()
			//antMatchers로 설정한 조건 외의 어떤 요청이든 '인증'해야 한다
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			// "접근 불가" 설정
			.authenticationEntryPoint(authenticationEntryPoint())
			.and()
			.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

	}


}
