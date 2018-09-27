package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;

@RelifeController
public class ControllerWithWrongReturnType {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public String sayHello(RelifeRequest request) {
        return "hello";
    }

}
