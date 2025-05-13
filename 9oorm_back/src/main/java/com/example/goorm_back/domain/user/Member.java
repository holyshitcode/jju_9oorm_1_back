package com.example.goorm_back.domain.user;

import com.example.goorm_back.domain.board.BoardComment;
import com.example.goorm_back.domain.board.CommunityBoard;
import com.example.goorm_back.domain.board.NoticeBoard;
import com.example.goorm_back.domain.clazz.ClazzReservation;
import com.example.goorm_back.domain.clazz.ReviewBoard;
import com.example.goorm_back.domain.utility.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private GenderType gender; //성별

    private String phoneNumber;

    private String userName;

    private String password;

    private String email;

    private String profileImage; //프로필 이미지

    @Enumerated(EnumType.STRING)
    private Role role; //일반 사용자와 사업자 구분

    private String address; // 거주지 주소

    @Column(unique = true)
    private String nickname;

    @OneToOne
    private Payment payment; //결제정보

    @OneToOne
    private Cart cart;

    @OneToOne
    private Bookmark bookmark;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<ReviewBoard> reviewBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<ClazzReservation> classReservations = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<CommunityBoard> communityBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<BoardComment> boardComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<NoticeBoard> noticeBoards = new ArrayList<>();

    @OneToOne
    private UserBusinessInfo userBusinessInfo; // 사업자정보 (사업자인 경우만 등록)




}
