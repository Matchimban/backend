package com.project.matchimban.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.project.matchimban.common.response.ResultData;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static Map<String, Object> exceptionMap;
    private static ObjectMapper mapper = new ObjectMapper();

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

        if (e instanceof HttpMessageNotReadableException) {
            if(e.getMessage().contains("not one of the values accepted for Enum class")){
                resultData = mapper.convertValue(exceptionMap.get(ErrorConstant.INPUT_ERROR_ENUM_VALIDATION), ResultData.class);
            }

            else {
                resultData = mapper.convertValue(exceptionMap.get(ErrorConstant.SERVER_ERROR_JSON_PARSE), ResultData.class);
            }
        }
        else {
            resultData = mapper.convertValue(exceptionMap.get(ErrorConstant.SERVER_ERROR_UNDEFINED), ResultData.class);
        }
        return new ResponseEntity<>(resultData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity serviceExceptionHandler(SVCException e) {
        ResultData resultData = new ResultData();
        if(exceptionMap.get(e.getCode()) != null){
            resultData = mapper.convertValue(exceptionMap.get(e.getCode()), ResultData.class);
        } else{
            log.error("정의되지 않은 에러: {}", e.getMessage(), e);
            resultData = mapper.convertValue(exceptionMap.get(ErrorConstant.SERVER_ERROR_UNDEFINED), ResultData.class);
            return new ResponseEntity<>(resultData, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(resultData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity validExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ResultData(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity handleMalformedJwtException(MalformedJwtException ex) {
        ResultData result = new ResultData();
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMsg("JWT 형식이 잘못되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity handleJwtSignatureException(SignatureException e) {
        ResultData result = new ResultData();
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMsg("변조된 서명입니다.");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
