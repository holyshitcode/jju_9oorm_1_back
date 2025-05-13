package com.example.goorm_back.service;

import com.example.goorm_back.domain.clazz.Clazz;
import com.example.goorm_back.domain.clazz.ClazzReservation;
import com.example.goorm_back.domain.clazz.ReservationStatus;
import com.example.goorm_back.domain.user.Member;
import com.example.goorm_back.dto.MakeClazzReservationRequestForm;
import com.example.goorm_back.dto.SaveClazzRequestForm;
import com.example.goorm_back.repository.ClazzRepository;
import com.example.goorm_back.repository.ClazzReservationRepository;
import com.example.goorm_back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClazzService {

    private final ClazzRepository clazzRepository;
    private final MemberRepository memberRepository;
    private final ClazzReservationRepository reservationRepository;

    public void saveClazz(Clazz clazz) {
        clazzRepository.save(clazz);
    }

    public void saveClazz(SaveClazzRequestForm saveClazzRequestForm) {
        Clazz clazz = new Clazz();
        clazz.setClazzTitle(saveClazzRequestForm.getClazzTitle());
        clazz.setClazzDescription(saveClazzRequestForm.getClazzDescription());
        clazz.setClazzPrice(saveClazzRequestForm.getClazzPrice());
        clazz.setClazzProfileImage(saveClazzRequestForm.getClazzProfileImage());
        clazz.setLatitude(saveClazzRequestForm.getLatitude());
        clazz.setLongitude(saveClazzRequestForm.getLongitude());
        clazz.setCategory(saveClazzRequestForm.getCategory());

        clazzRepository.save(clazz);
    }

    public void makeClazzReservation(Clazz clazz, Member member) {
        if (clazz.getReservationStatus().equals(ReservationStatus.ING)) {
            ClazzReservation clazzReservation = new ClazzReservation();
            clazzReservation.setClazz(clazz);
            clazzReservation.setMember(member);
            reservationRepository.save(clazzReservation);
        }
    }

    public void makeClazzReservation(MakeClazzReservationRequestForm makeClazzReservationRequestForm) {
        Clazz findClazz = clazzRepository.findByClazzTitle(makeClazzReservationRequestForm.getClazzTitle());
        if (findClazz == null) {
            throw new IllegalArgumentException("해당 클래스가 존재하지 않습니다: " + makeClazzReservationRequestForm.getClazzTitle());
        }
        Member findMember = memberRepository.findByUserName(makeClazzReservationRequestForm.getUserName());
        /*
        아직 회원가입/로그인은 구현되지 않았기 때문에 예외처리 주석으로 해놓겠습니다.
        if (findMember == null) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + makeClazzReservationRequestForm.getUserName());
        }
        */

        ClazzReservation clazzReservation = new ClazzReservation();
        clazzReservation.setClazz(findClazz);
        clazzReservation.setMember(findMember);
        clazzReservation.setReservationTime(makeClazzReservationRequestForm.getReservationTime());
        clazzReservation.setReservationDate(makeClazzReservationRequestForm.getReservationDate());
        clazzReservation.setReservationInviteTime(makeClazzReservationRequestForm.getReservationInviteTime());
        clazzReservation.setPeopleCount(makeClazzReservationRequestForm.getPeopleCount());
        reservationRepository.save(clazzReservation);
    }



}
