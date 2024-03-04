package com.project.matchimban.api.auth.email.repository;

import com.project.matchimban.api.auth.email.domain.entity.EmailAuthCode;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuthCode, String> {
}
