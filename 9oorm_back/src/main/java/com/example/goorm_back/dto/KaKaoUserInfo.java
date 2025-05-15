package com.example.goorm_back.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KaKaoUserInfo {


	private Long id;
	private String connected_at;
	private KakaoAccount kakao_Account;


	public static class KakaoAccount {

		private Boolean profile_needs_agreement; //false 프로필 정보는 무조건 옴
		private Profile profile;
	}


	public static class Profile {

		private String nickname;
		private String profile_image_url;
		private String thumbnail_image_url;
		private Boolean is_default_image;
	}


}
