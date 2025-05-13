package com.example.goorm_back.domain.clazz;

import com.example.goorm_back.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Clazz {

    @Id @GeneratedValue
    @Column(name = "clazz_id")
    private Long id;

    private String clazzTitle;

    private String clazzDescription; //클래스 소개

    private Long clazzPrice; //클래스 가격

    private String clazzProfileImage;

    private String latitude; // 클래스 위도

    private String longitude; // 클래스 경도

    private Category category;

    @OneToOne
    private Member owner; // 클래스 등록자

    /**
     *  리뷰 생길때 ratingCounts, ratingAvg 변동예정
     */
    private Long ratingCounts; //평점 총개수

    private Double ratingAvg; // 평점 평균

    private Long bookmarkCount;

    private Long viewCount;

    private ClazzAcceptStatus acceptStatus; //예약 가능여부

    @OneToMany(mappedBy = "clazz", orphanRemoval = true)
    private List<ReviewBoard> reviewBoard; //리뷰

    @OneToMany(mappedBy = "clazz", orphanRemoval = true)
    private List<ClazzReservation> clazzReservations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;





}
