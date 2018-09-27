package com.tw.relife;

import java.lang.reflect.InvocationTargetException;

public interface RelifeAppHandler {
    RelifeResponse process(RelifeRequest request) throws InvocationTargetException, IllegalAccessException;
}
