package com.example.goorm_back.domain.clazz;

import com.example.goorm_back.domain.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class ClazzReservation {

    @Id @GeneratedValue
    @Column(name = "clazz_reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private LocalDateTime reservationTime; // 예약한 시간

    private LocalDate reservationDate; // 예약한 날짜

    private LocalTime reservationInviteTime; // 예약 도착시간

    private int PeopleCount; // 예약 인원 수

    /*
    * 클래스 예약 날짜, 클래스 예약 시작 시간, 예약 인원 수 추가해야 될 것 같습니다.
    * 추가로 예약 취소를 하게되면 환불을 해야하니
    * 결제 정보도 가지고 있어야 하는건지..?!
    */


}
