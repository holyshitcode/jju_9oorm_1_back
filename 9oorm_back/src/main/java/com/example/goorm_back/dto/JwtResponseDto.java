package com.example.goorm_back.dto;

import lombok.Getter;
import lombok.Setter;

// 로그인(토큰 재발급) 성공 시
@Setter
@Getter
public class JwtResponseDto {

	private boolean isJwtSuccess;
	private String accessToken;
	private String refreshToken; //선택 사항

}
