package com.project.matchimban.comments.repository;

import com.project.matchimban.comments.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
