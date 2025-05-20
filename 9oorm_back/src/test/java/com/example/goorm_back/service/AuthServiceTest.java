package com.example.goorm_back.service;

import com.example.goorm_back.dto.KakaoTokenResponseDto;
import com.example.goorm_back.dto.KakaoUserInfoDto;
import com.example.goorm_back.dto.KakaoUserInfoDto.KakaoAccount;
import com.example.goorm_back.dto.KakaoUserInfoDto.Profile;
import com.example.goorm_back.jwt.JwtTokenProvider;
import com.example.goorm_back.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {

	@Mock
	private MemberRepository memberRepository = mock(MemberRepository.class);
	@Mock
	private JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);

	@InjectMocks
	private AuthService authService;

	@BeforeEach
	void setUp() {
		authService = new AuthService(memberRepository, jwtTokenProvider);
		when(jwtTokenProvider.generateToken(any(), any(), any()))
			.thenReturn("mocked-jwt-token");
	}

	@Test
	void 카카오_로그인_성공_응답_확인_하자() {
		// given
		String testToken = "test-kakao-access-token";

		// spy 사용
		AuthService spyService = spy(authService);

		// 테스트용 유저 정보 생성
		Profile profile = new Profile();
		profile.setNickname("테스트계정");

		KakaoAccount kakaoAccount = new KakaoAccount();
		kakaoAccount.setEmail("test@example.com");
		kakaoAccount.setProfile(profile);

		KakaoUserInfoDto userInfo = new KakaoUserInfoDto();
		userInfo.setId(12345L);
		userInfo.setKakaoAccount(kakaoAccount);

		doReturn(userInfo).when(spyService).getKakaoInfo(testToken);

		// when
		ResponseEntity<KakaoTokenResponseDto> response = spyService.kakaoLogin(testToken);

		// then
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getCode()).isEqualTo("AUTH-200");
		assertThat(response.getBody().getIsKakaoSuccess()).isTrue();
	}

	@Test
	void 카카오_로그인_성공_응답_확인_ㄹㅇ() {
		String testToken = "test-kakao-access-token";
		AuthService spyService = spy(authService);

		KakaoUserInfoDto.Profile profile = new KakaoUserInfoDto.Profile();
		profile.setNickname("테스트계정");

		KakaoUserInfoDto.KakaoAccount kakaoAccount = new KakaoUserInfoDto.KakaoAccount();
		kakaoAccount.setEmail("test@example.com");
		kakaoAccount.setProfile(profile);

		KakaoUserInfoDto userInfo = new KakaoUserInfoDto();
		userInfo.setId(12345L);
		userInfo.setKakaoAccount(kakaoAccount);

		doReturn(userInfo).when(spyService).getKakaoInfo(testToken);

		ResponseEntity<KakaoTokenResponseDto> response = spyService.kakaoLogin(testToken);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getCode()).isEqualTo("AUTH-200");
		assertThat(response.getBody().getIsKakaoSuccess()).isTrue();
	}

	@Test
	void 로그인_되고_JWT_발급_하고_있니() {
		//given
		String testToken = "test-jwt-access-token";
		AuthService spyService = spy(authService);

		Profile profile = new Profile();
		profile.setNickname("테스트계정");

		KakaoAccount kakaoAcount = new KakaoAccount();
		kakaoAcount.setEmail("test@example.com");
		kakaoAcount.setProfile(profile);

		KakaoUserInfoDto userInfo = new KakaoUserInfoDto();
		userInfo.setId(12345L);
		userInfo.setKakaoAccount(kakaoAcount);

		doReturn(userInfo)
			.when(spyService)
			.getKakaoInfo(testToken);
		when(jwtTokenProvider.generateToken(12345L, "test@example.com", "USER"))
			.thenReturn("mocked-jwt-token");

		// when
		ResponseEntity<KakaoTokenResponseDto> response = spyService.kakaoLogin(testToken);

		//then
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody().getCode()).isEqualTo("AUTH-200");
		assertThat(response.getBody().getAccess_token()).isEqualTo("mocked-jwt-token");
	}

}
