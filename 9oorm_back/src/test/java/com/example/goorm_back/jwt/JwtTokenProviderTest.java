package com.example.goorm_back.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {

	private JwtTokenProvider jwtTokenProvider;

	@BeforeEach
	void setUp() {
		jwtTokenProvider = new JwtTokenProvider();
	}

	@Test
	void 토큰_생성_및_유효성_검사() {
		// given
		Long userId = 123L;
		String email = "test@example.com";
		String role = "USER";

		// when
		String token = jwtTokenProvider.generateToken(userId, email, role);

		// then
		assertThat(token).isNotNull();
		assertThat(jwtTokenProvider.validateToken(token)).isTrue();
	}

	@Test
	void 토큰에서_userId_추출_확인() {
		// given
		Long userId = 456L;
		String token = jwtTokenProvider.generateToken(userId, "test@aaa.com", "ADMIN");

		// when
		Long extractedId = jwtTokenProvider.getUserId(token);

		// then
		assertThat(extractedId).isEqualTo(userId);
	}

	@Test
	void 요청에서_토큰_추출_테스트() {
		// given
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		String fakeToken = "Bearer fake.jwt.token";
		Mockito.when(request.getHeader("Authorization")).thenReturn(fakeToken);

		// when
		String extracted = jwtTokenProvider.resolveToken(request);

		// then
		assertThat(extracted).isEqualTo("fake.jwt.token");
	}

	@Test
	void 환경변수_불러오기_테스트() {
		String token = jwtTokenProvider.generateToken(1L, "a@a.com", "USER");
		assertThat(token).isNotNull();
		System.out.println("🧪 Generated Token: " + token);
	}
}