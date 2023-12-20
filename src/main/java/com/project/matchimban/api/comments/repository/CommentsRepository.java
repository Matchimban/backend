package com.project.matchimban.api.comments.repository;

import com.project.matchimban.api.comments.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
