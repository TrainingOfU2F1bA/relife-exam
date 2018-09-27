package com.tw.relife;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;

public interface RelifeAppHandler {
    RelifeResponse process(RelifeRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException;
}
