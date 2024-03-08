package com.project.matchimban.common.test;

import com.project.matchimban.common.exception.ErrorConstant;
import com.project.matchimban.common.exception.SVCException;
import com.project.matchimban.common.global.FileInfo;
import com.project.matchimban.common.modules.S3Service;
import com.project.matchimban.common.response.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Tag(name = "Test", description = "테스트 API")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;
    private final TestService testService;
    private final S3Service s3Service;

    @Operation(summary = "(테스트)이름 조회", description = "id값을 통해 name을 조회합니다.")
    @Parameter(name = "id", description = "고유ID", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = TestRes.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/api/test1/{id}")
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
    @PostMapping("/api/test1/{name}")
    public String saveName(@PathVariable String name) {
        Test test = new Test();
        test.setName(name);
        testRepository.save(test);
        return "회원 저장 완료";
    }


    @PostMapping("/api/test2/{etc}")
    public ResponseEntity exceptionTest(@Validated @RequestBody TestDto dto, @PathVariable String etc) {

        if(etc.equals("first")) throw new SVCException(ErrorConstant.TEST_COUPON_ERROR_NONE_PK);
        else if(etc.equals("second")) throw new SVCException(ErrorConstant.TEST_ETC);
        else if(etc.equals("third")) throw new SVCException(".");
        else if (etc.equals("trace")) testService.stackTraceTest();
        return new ResponseEntity(new ResultData(), HttpStatus.OK);
    }

    @PostMapping("/api/file/upload")
    public String s3FileUploadTest(@RequestPart(value = "file") MultipartFile file) {
        Optional<FileInfo> fileInfo = s3Service.saveFile(file);
        if (fileInfo.isPresent()) return "성공";
        else return "실패";
    }
}
