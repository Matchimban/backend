package com.project.matchimban;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 기능", description = "Test API Document")
public class TestController {

    private final TestRepository testRepository;

    @PostMapping("/test/{name}")
    @Operation(summary = "로그인", description = "test 설명")
    public String test(@PathVariable String name) {
        Test test = new Test();
        test.setName(name);
        testRepository.save(test);
        return "completed";
    }
//
//    @GetMapping("/test/{name}")
//    @Operation(summary = "회원가입", description = "test 설명2")
//    public String test2() {
//        Test test = new Test();
//        testRepository.save(test);
//        return "completed";
//    }
//
//    @PostMapping("/restaurant/{name}")
//    @Operation(summary = "매장 가입", description = "test 설명3")
//    public String test3(@PathVariable String name) {
//        Test test = new Test();
//        test.setName(name);
//        testRepository.save(test);
//        return "completed";
//    }
//
//    @GetMapping("/restaurant/{name}")
//    @Operation(summary = "매장 조회", description = "test 설명4")
//    public String test4() {
//        Test test = new Test();
//        testRepository.save(test);
//        return "completed";
//    }
//
//    @PostMapping("/test3/{name}")
//    @Operation(summary = "매장 가입", description = "test 설명3")
//    public TestDTO test5(@PathVariable String name) {
//        TestDTO test = new TestDTO();
//        //test.setName(name);
//        return new TestDTO();
//    }
//
//    @GetMapping("/test3/{name}")
//    @Operation(summary = "매장 조회", description = "test 설명4")
//    public TestDTO test6() {
//        TestDTO test = new TestDTO();
//        return new TestDTO();
//    }
}
