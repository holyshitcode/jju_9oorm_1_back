package com.example.goorm_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoDto {

	private Long id;

	@JsonProperty("connected_at")
	private String connectedAt;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@Setter
	public static class KakaoAccount {

		private String email;

		@JsonProperty("profile_needs_agreement")
		private Boolean profileNeedsAgreement;

		private Profile profile;


	}

	@Getter
	@Setter
	public static class Profile {

		private String nickname;

		@JsonProperty("profile_image_url")
		private String profileImageUrl;

		@JsonProperty("thumbnail_image_url")
		private String thumbnailImageUrl;

		@JsonProperty("is_default_image")
		private Boolean isDefaultImage;
	}
}
