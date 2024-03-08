package com.project.matchimban.common.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void stackTraceTest(){
        int n = 3/0;
    }

}
