package com.example.goorm_back.service;

import com.example.goorm_back.domain.user.Member;
import com.example.goorm_back.dto.KaKaoUserInfo;
import com.example.goorm_back.dto.LoginResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	public KaKaoUserInfo getKakaoInfo(String kakaoAccessToken) {
		// 1. 카카오 유저 정보 요청 url
		String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

		// 2. 요청 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + kakaoAccessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// 3. RestTemplate으로 POST 요청
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
			kakaoUserInfoUrl,
			HttpMethod.POST,
			entity,
			String.class
		);

		// 4. JSON → DTO 매핑
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			return objectMapper.readValue(response.getBody(), KaKaoUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("카카오 유저 정보 파싱 실패");
		}
	}


}

