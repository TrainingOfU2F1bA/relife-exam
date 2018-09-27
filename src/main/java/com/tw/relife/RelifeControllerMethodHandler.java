package com.tw.relife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RelifeControllerMethodHandler implements  RelifeAppHandler {

    private Object controller;
    private Method method;

    public RelifeControllerMethodHandler(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        Object returnValue = method.invoke(controller, request);
        if (!RelifeResponse.class.isInstance(returnValue)) {
            String content = new ObjectMapper().writeValueAsString(returnValue);
            return new RelifeResponse(200, content,"application/json");
        }
        return (RelifeResponse) returnValue;
    }
}
