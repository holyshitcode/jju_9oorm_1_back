package com.example.goorm_back.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//카카오 로그인 처리 후 프론트에 전달할 응답 데이터 객체 (DTO)
public class KakaoTokenResponseDto {

	private String access_token; // 액세스 토큰
	private String token_type; // 토큰 타입, 예: "bearer"
	private String refresh_token; // 리프레시 토큰
	private Integer expires_in; // 인증된 범위
	private Integer refresh_token_expires_in; // 리프레시 토큰 만료 시간(초)

	private Boolean isKakaoSuccess;
	private String code;
	private String message;
}
