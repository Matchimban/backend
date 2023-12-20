package com.project.matchimban.api.likes.repository;

import com.project.matchimban.api.likes.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
