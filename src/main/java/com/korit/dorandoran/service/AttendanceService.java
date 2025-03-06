package com.korit.dorandoran.service;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.AttendEntity;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface AttendanceService {
    ResponseEntity<ResponseDto> checkAttendance(String userId);
    ResponseEntity<List<AttendEntity>> getAttendanceRecords(String userId);
}
