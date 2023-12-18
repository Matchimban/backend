package com.project.matchimban.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.matchimban.common.response.ResultCodeConstant;
import com.project.matchimban.common.response.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static Map<String, Object> exceptionMap;

    @Value("${env.exceptionPath}")
    private Resource exceptionYmlFile;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        exceptionMap = objectMapper.readValue(exceptionYmlFile.getInputStream(), Map.class);
    }

    @ExceptionHandler
    public ResponseEntity serverExceptionHandler(Exception e) {
        log.error("정의되지 않거나 서버 에러: {}", e.getMessage(), e);
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCodeConstant.SERVER_FAIL_CD);
        resultData.setMsg(e.getMessage());
        return new ResponseEntity<>(resultData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity serviceExceptionHandler(SVCException e) {
        ResultData resultData = new ResultData();
        if(exceptionMap.get(e.getCode()) != null){
            resultData.setCode(ResultCodeConstant.SVC_FAIL_CD);
            resultData.setResult(exceptionMap.get(e.getCode()));
        } else{
            log.error("정의되지 않은 에러: {}", e.getMessage(), e);
            resultData.setCode(ResultCodeConstant.SERVER_FAIL_CD);
            resultData.setMsg(ErrorConstant.UNDEFINED_ERROR);
            return new ResponseEntity<>(resultData, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(resultData, HttpStatus.BAD_REQUEST);
    }

}
