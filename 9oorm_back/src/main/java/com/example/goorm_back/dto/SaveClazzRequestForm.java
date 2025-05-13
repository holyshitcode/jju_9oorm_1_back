package com.example.goorm_back.dto;

import com.example.goorm_back.domain.clazz.Category;
import lombok.Data;

@Data
public class SaveClazzRequestForm {
    private String clazzTitle; // 클래스명
    private String clazzDescription; // 클래스 설명
    private Long clazzPrice; // 클래스 가격
    private String clazzProfileImage; // 클래스 대표이미지
    private String latitude; // 위도
    private String longitude; // 경도
    private Category category; // 카테고리
}
