package com.project.matchimban.common.response;

import lombok.Data;

@Data
public class ResultData {
    int code = ResultCodeConstant.SUC_CD;
    String msg;
    Object result;
}
