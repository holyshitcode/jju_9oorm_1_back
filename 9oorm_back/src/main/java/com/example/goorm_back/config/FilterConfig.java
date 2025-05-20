//package com.example.goorm_back.config;
//
//import com.example.goorm_back.jwt.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class FilterConfig {
//
//	private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http
//			.csrf().disable()
//			.authorizeHttpRequests(auth -> auth
//				.requestMatchers("/auth/**").permitAll() // 로그인, 회원가입은 허용
//				.anyRequest().authenticated() // 나머지는 인증 필요
//			)
//			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//			.build();
//	}
//}