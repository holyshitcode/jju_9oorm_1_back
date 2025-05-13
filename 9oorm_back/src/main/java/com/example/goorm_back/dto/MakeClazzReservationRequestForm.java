package com.example.goorm_back.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MakeClazzReservationRequestForm {
    private String clazzTitle;
    private String userName;
    private LocalDateTime reservationTime; // 예약한 시간
    private LocalDate reservationDate; // 예약 클래스 시작 날짜
    private LocalTime reservationInviteTime; // 예약 클래스 시작 시간
    private int peopleCount; // 인원 수
}
