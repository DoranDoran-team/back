package com.korit.dorandoran.common.object;

public enum NotificationType {
    MILEAGE_EARNED,     // 마일리지 적립
    REFUND_APPROVED,    // 환급 승인
    REFUND_DENIED,      // 환급 반려
    REFUND_REQUESTED,   // 관리자: 새로운 환급 요청 발생
    REPORT_RECEIVED,     // 관리자: 새로운 신고 발생
    BIRTHDAY            // 생일 축하 알림
}