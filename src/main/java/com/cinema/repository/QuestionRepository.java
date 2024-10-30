package com.cinema.repository;

import com.cinema.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
  @Query("SELECT q FROM Question q ORDER BY q.qDate DESC") // 개봉일 기준으로 최신순으로 정렬하는 쿼리
  Page<Question> findLatestByDate(Pageable pageable); // 페이징 기능을 지원함
}
