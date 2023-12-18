package com.project.matchimban.common.exception;

import lombok.Getter;

@Getter
public class SVCException extends RuntimeException{

    private String code;

    public SVCException(String code){
        this.code=code;
    }

    public SVCException() {
        super();
    }

    public SVCException(String message, Throwable cause) {
        super(message, cause);
    }

    public SVCException(Throwable cause) {
        super(cause);
    }

    protected SVCException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
