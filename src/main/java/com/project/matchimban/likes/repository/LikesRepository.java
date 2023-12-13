package com.project.matchimban.likes.repository;

import com.project.matchimban.likes.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
