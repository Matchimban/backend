package com.project.matchimban.api.follow.repository;

import com.project.matchimban.api.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
