package com.project.matchimban;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @PostMapping("/test/{name}")
    public String test(@PathVariable String name){
        Test test = new Test();
        test.setName(name);
        testRepository.save(test);
        return "completed";
    }
}
