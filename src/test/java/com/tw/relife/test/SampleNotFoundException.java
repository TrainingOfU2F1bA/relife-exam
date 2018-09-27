package com.tw.relife.test;

import com.tw.relife.annotation.RelifeStatusCode;

@RelifeStatusCode(404)
public class SampleNotFoundException extends RuntimeException {
    public SampleNotFoundException() {
        super();
    }

    public SampleNotFoundException(String message) {
        super(message);
    }

    public SampleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SampleNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SampleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
