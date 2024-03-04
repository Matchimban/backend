package com.project.matchimban.api.auth.email.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Schema(description = "이메일 제목 및 내용 DTO")
@Getter
@Builder
public class EmailContentsDTO {
    @Schema(description = "제목")
    @NotBlank
    private String subject;

    @Schema(description = "내용")
    @NotBlank
    private String content;

}