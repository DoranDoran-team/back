package com.korit.dorandoran.controller;

import com.korit.dorandoran.entity.AttendEntity;
import com.korit.dorandoran.service.AttendanceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check")
    public ResponseEntity<?> checkAttendance(@AuthenticationPrincipal String userId) {
        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return attendanceService.checkAttendance(userId);
    }

    @GetMapping
    public ResponseEntity<List<AttendEntity>> getAttendanceRecords(@AuthenticationPrincipal String userId) {
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return attendanceService.getAttendanceRecords(userId);
    }
}
