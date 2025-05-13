package com.example.goorm_back.controller;

import com.example.goorm_back.dto.MakeClazzReservationRequestForm;
import com.example.goorm_back.dto.SaveClazzRequestForm;
import com.example.goorm_back.service.ClazzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/clazz")
@RequiredArgsConstructor
public class ClazzController {

    private final ClazzService clazzService;

    @PostMapping("/saveClazz")
    public void saveClazz(@RequestBody SaveClazzRequestForm saveClazzRequestForm){
        clazzService.saveClazz(saveClazzRequestForm);
    }

    @PostMapping("/makeClazzReservation")
    public void makeClazzReservation(@RequestBody MakeClazzReservationRequestForm makeClazzReservationRequestForm){
        clazzService.makeClazzReservation(makeClazzReservationRequestForm);
    }

}
