package com.example.goorm_back.controller;

import static com.example.goorm_back.dto.KakaoTokenResponseDto.*;

import com.example.goorm_back.dto.KakaoTokenResponseDto;
import com.example.goorm_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/kakao/callback")
	public ResponseEntity<KakaoTokenResponseDto> kakaoLogin(
		@RequestParam(required = false) String code) {

		//  인가코드 누락된 경우 예외 처리
		if (code == null || code.isBlank()) {
			KakaoTokenResponseDto errorResponse = builder()
				.isKakaoSuccess(false)
				.code("AUTH-001")
				.message("인가 코드가 전달되지 않았습니다.")
				.build();

			return ResponseEntity.badRequest().body(errorResponse);
		}

		String kakaoAccessToken = authService.getKakaoAccessToken(code);
		return authService.kakaoLogin(kakaoAccessToken);
	}


}
