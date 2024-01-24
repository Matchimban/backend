package com.project.matchimban.api.menu.repository;

import com.project.matchimban.api.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
