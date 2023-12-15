package com.project.matchimban;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Test", description = "테스트 API")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @Operation(summary = "(테스트)이름 조회", description = "id값을 통해 name을 조회합니다.")
    @Parameter(name = "id", description = "고유ID", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TestRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/test2/{id}")
    public String findNameById(@PathVariable long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.isPresent() ? test.get().getName() : "존재하지 않는 번호입니다.";
    }

    @Operation(summary = "(테스트)회원 등록", description = "회원 이름을 등록합니다.")
    @Parameter(name = "name", description = "회원 이름", example = "'홍길동'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TestRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/api/test2/{name}")
    public String saveName(@PathVariable String name) {
        Test test = new Test();
        test.setName(name);
        testRepository.save(test);
        return "회원 저장 완료";
    }
}
