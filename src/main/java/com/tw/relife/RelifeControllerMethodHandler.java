package com.tw.relife;

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
    public RelifeResponse process(RelifeRequest request) throws InvocationTargetException, IllegalAccessException {
        return (RelifeResponse) method.invoke(controller,request);
    }
}
