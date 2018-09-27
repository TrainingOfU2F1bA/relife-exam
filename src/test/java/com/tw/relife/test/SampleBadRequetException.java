package com.tw.relife.test;

import com.tw.relife.annotation.RelifeStatusCode;

@RelifeStatusCode(400)
public class SampleBadRequetException extends RuntimeException {
    public SampleBadRequetException() {
        super();
    }

    public SampleBadRequetException(String message) {
        super(message);
    }

    public SampleBadRequetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SampleBadRequetException(Throwable cause) {
        super(cause);
    }

    protected SampleBadRequetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
