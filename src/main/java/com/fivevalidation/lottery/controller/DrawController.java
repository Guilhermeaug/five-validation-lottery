package com.fivevalidation.lottery.controller;

import com.fivevalidation.lottery.dto.response.DrawResult;
import com.fivevalidation.lottery.service.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("draws")
public class DrawController {

    private final DrawService drawService;

    @GetMapping
    ResponseEntity<List<DrawResult>> getDrawResult() {
       return ResponseEntity.ok(drawService.getDrawResult());
    }

}
