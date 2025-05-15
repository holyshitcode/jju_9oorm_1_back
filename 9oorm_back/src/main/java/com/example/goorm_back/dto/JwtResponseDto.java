package com.example.goorm_back.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class JwtResponseDto {

	private boolean sucess;
	private String accessToken;
	private String refreshToken; //선택 사항

}
