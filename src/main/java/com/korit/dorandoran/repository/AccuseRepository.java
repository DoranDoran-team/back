package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.korit.dorandoran.common.object.ReportType;
import com.korit.dorandoran.entity.AccuseEntity;

public interface AccuseRepository extends JpaRepository<AccuseEntity, Integer> {

  // 댓글 중복신고 확인 메서드
  boolean existsByUserIdAndReportTypeAndReplyId(String userId, ReportType reportType, Integer replyId);

  // 게시글 중복신고 확인 메서드
  boolean existsByUserIdAndReportTypeAndPostId(String userId, ReportType reportType, Integer postId);

  List<AccuseEntity> findAllByOrderByAccuseIdAsc();
}
