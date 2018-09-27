package com.tw.relife;

import com.tw.relife.annotation.RelifeStatusCode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RelifeApp implements RelifeAppHandler {
    private final RelifeAppHandler handler;

    public RelifeApp(RelifeAppHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        try {
            return handler.process(request);
        } catch (Exception e) {
            RelifeStatusCode annotation = e.getClass().getAnnotation(RelifeStatusCode.class);
            if (annotation != null) {
                return new RelifeResponse(annotation.value());
            }
            return new RelifeResponse(500);
        }
    }
}
