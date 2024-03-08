package com.project.matchimban.common.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AuthorizationTest {

    @GetMapping("/user/test")
    @PreAuthorize("hasRole('USER')")
    public String test1() {
        return "user";
    }
    @GetMapping("/owner/test")
    public String test2() {
        return "owner";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ADMIN')")
    public String test3() {
        return "admin";
    }
}
