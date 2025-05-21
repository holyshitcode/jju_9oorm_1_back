package com.example.goorm_back.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtTokenProviderTest {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@DynamicPropertySource
	static void overrideJwtProps(DynamicPropertyRegistry registry) {
		registry.add("jwt.secret", () -> "test-only-secret-key-should-not-use-in-prod");
		registry.add("jwt.expiration", () -> "86400000");
	}

	@Test
	@DisplayName("토큰 생성 및 유효성 검사")
	void generateAndValidateToken() {
		// given
		Long userId = 1L;
		String email = "test@example.com";
		String role = "USER";

		// when
		String token = jwtTokenProvider.generateToken(userId, email, role);

		// then
		assertThat(token).isNotBlank();
		assertThat(jwtTokenProvider.validateToken(token)).isTrue();
	}

	@Test
	@DisplayName("토큰에서 사용자 ID 추출 확인")
	void extractUserIdFromToken() {
		// given
		Long expectedUserId = 999L;
		String token = jwtTokenProvider.generateToken(expectedUserId, "aaa@aaa.com", "ADMIN");

		// when
		Long actualUserId = jwtTokenProvider.getUserId(token);

		// then
		assertThat(actualUserId).isEqualTo(expectedUserId);
	}

	@Test
	@DisplayName("요청에서 Bearer 토큰 추출 확인")
	void extractTokenFromRequest() {
		// given
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(mockRequest.getHeader("Authorization")).thenReturn("Bearer abc.def.ghi");

		// when
		String result = jwtTokenProvider.resolveToken(mockRequest);

		// then
		assertThat(result).isEqualTo("abc.def.ghi");
	}

	@Test
	@DisplayName("환경 변수 기반 시크릿키 및 만료시간 설정 확인")
	void checkSecretKeyAndExpirationFromEnv() {
		// when
		String token = jwtTokenProvider.generateToken(777L, "env@test.com", "USER");

		// then
		assertThat(token).isNotNull();
		System.out.println(" 환경변수로 생성된 토큰: " + token);
	}
}