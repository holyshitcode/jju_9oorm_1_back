package com.example.goorm_back.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KakaoTokenResponse {

	private String access_token; // 액세스 토큰
	private String token_type; // 토큰 타입, 예: "bearer"
	private String refresh_token; // 리프레시 토큰
	private Integer expires_in; // 인증된 범위
	private Integer refresh_token_expires_in; // 리프레시 토큰 만료 시간(초)

	private Boolean success;
	private String code;
	private String message;
}
