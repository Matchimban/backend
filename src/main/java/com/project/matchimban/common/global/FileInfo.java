package com.project.matchimban.common.global;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FileInfo {

    private String originalFileName;

    private String savedFileUrl;

}
