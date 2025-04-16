package com.korit.dorandoran.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.korit.dorandoran.common.object.ChatRoomStatus;
import com.korit.dorandoran.entity.chatting.ChatMessage;
import com.korit.dorandoran.entity.chatting.ChatReservation;
import com.korit.dorandoran.entity.chatting.ChatRoom;
import com.korit.dorandoran.service.ChatRoomService;
import com.korit.dorandoran.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;
    private final FileService fileService;

    @PostMapping(value = "/rooms", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestParam("roomName") String roomName,
            @RequestParam("scheduledTime") String scheduledTimeStr,
            @RequestParam("description") String description,
            @RequestParam(value = "proTeam", required = false) List<String> proTeam,
            @RequestParam(value = "conTeam", required = false) List<String> conTeam,
            @RequestPart(value = "representativeImage", required = false) MultipartFile file) {

        LocalDateTime scheduledTime;
        try {
            scheduledTime = LocalDateTime.parse(scheduledTimeStr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        if (proTeam == null) {
            proTeam = new ArrayList<>();
        }
        if (conTeam == null) {
            conTeam = new ArrayList<>();
        }

        String repImgUrl;
        if (file != null && !file.isEmpty()) {
            repImgUrl = fileService.upload(file);
        } else {
            repImgUrl = "/defaultChatRoomImage.png";
        }

        ChatRoomStatus status = ChatRoomStatus.WAITING;

        ChatRoom chatRoom = new ChatRoom(roomName, proTeam, conTeam, status, scheduledTime, repImgUrl, description);

        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoom);
        return ResponseEntity.ok(createdRoom);
    }

    // 채팅방 상세 정보 조회
    @GetMapping("/rooms/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ChatRoom> getChatRoom(@PathVariable("roomId") String roomId) {
        ChatRoom room = chatRoomService.getChatRoomById(roomId);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    // 사용자가 채팅방 예약 신청
    @PostMapping("/rooms/{roomId}/reserve")
    public ResponseEntity<ChatReservation> reserveChatRoom(@PathVariable("roomId") String roomId,
            @RequestParam("userIds") List<String> userIds) {
        ChatReservation reservation = chatRoomService.reserveChatRoom(roomId, userIds);
        return ResponseEntity.ok(reservation);
    }

    // 예약 취소 (단일 예약 취소 처리)
    @PostMapping("/rooms/{roomId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable("roomId") String roomId,
            @RequestParam("userId") String userId) {
        chatRoomService.cancelReservation(roomId, userId);
        return ResponseEntity.ok().build();
    }

    // 15분 전 준비 확인 (관리자 호출 또는 스케줄러에서 호출)
    @PostMapping("/rooms/{roomId}/confirm")
    public ResponseEntity<Void> confirmReservation(@PathVariable("roomId") String roomId) {
        chatRoomService.confirmReservation(roomId);
        return ResponseEntity.ok().build();
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms/list")
    public ResponseEntity<List<ChatRoom>> getChatRooms() {
        List<ChatRoom> rooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(rooms);
    }

    // 채팅방 내 메시지 조회
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessagesByRoom(@PathVariable("roomId") String roomId) {
        List<ChatMessage> messages = chatRoomService.getMessagesByRoom(roomId);
        return ResponseEntity.ok(messages);
    }

    // 토론 종료
    @PostMapping("/rooms/{roomId}/control")
    public ResponseEntity<Void> controlChatRoom(@PathVariable("roomId") String roomId,
            @RequestBody String status) {
        if ("ENDED".equalsIgnoreCase(status)) {
            chatRoomService.endChatRoom(roomId);
        }
        return ResponseEntity.ok().build();
    }
}
