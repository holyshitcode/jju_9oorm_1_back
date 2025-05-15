package com.example.goorm_back.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
	boolean success;
	String nickname;
	Long id;

}
