package com.example.goorm_back.service;

import com.example.goorm_back.dto.JwtResponseDto;
import com.example.goorm_back.dto.KakaoTokenResponseDto;
import com.example.goorm_back.dto.KakaoUserInfoDto;
import com.example.goorm_back.jwt.JwtTokenProvider;
import com.example.goorm_back.repository.MemberRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;

	// 1. 인가코드로 카카오 AccessToken 요청
	public String getKakaoAccessToken(String code) {
		String tokenUrl = "https://kauth.kakao.com/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "여기에_카카오_REST_API_키");
		body.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
		body.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			tokenUrl,
			HttpMethod.POST,
			request,
			String.class
		);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			return jsonNode.get("access_token").asText();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("카카오 AccessToken 파싱 실패");
		}
	}

	// 2. AccessToken으로 카카오 유저 정보 요청
	public KakaoUserInfoDto getKakaoInfo(String kakaoAccessToken) {
		String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + kakaoAccessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(
			kakaoUserInfoUrl,
			HttpMethod.POST,
			entity,
			String.class
		);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return objectMapper.readValue(response.getBody(), KakaoUserInfoDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("카카오 유저 정보 파싱 실패");
		}
	}

	// 3. 로그인 처리
	public ResponseEntity<KakaoTokenResponseDto> kakaoLogin(String kakaoAccessToken) {
		try {
			KakaoUserInfoDto userInfo = getKakaoInfo(kakaoAccessToken);
			String token = jwtTokenProvider.generateToken(
				userInfo.getId(),
				userInfo.getKakaoAccount().getEmail(),
					userInfo.getKakaoAccount().getProfile().getNickname()
					);

			String email = userInfo.getKakaoAccount().getEmail();
			String nickname = userInfo.getKakaoAccount().getProfile().getNickname();

			System.out.println("✨ 카카오 유저 정보:");
			System.out.println("ID: " + userInfo.getId());
			System.out.println("Email: " + email);
			System.out.println("Nickname: " + nickname);

			return ResponseEntity.ok(
				KakaoTokenResponseDto.builder()
					.isKakaoSuccess(true)
					.code("AUTH-200")
					.message("카카오 로그인 accessToken 확인 완료")
					.access_token(token)
					.build()
			);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(
				KakaoTokenResponseDto.builder()
					.isKakaoSuccess(true)
					.code("AUTH-500")
					.message("카카오 로그인 중 오류 발생")
					.build()
			);
		}
	}
}