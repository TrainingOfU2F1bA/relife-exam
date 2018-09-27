package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;

@RelifeController
public class MutipleActionController {

    @RelifeRequestMapping(value = "/hi", method = RelifeMethod.GET)
    public RelifeResponse sayHi(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hi from " + request.getPath(),
                "text/plain");
    }

    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public RelifeResponse sayHello(RelifeRequest request) {
        return new RelifeResponse(
                200,
                "Hello from " + request.getPath(),
                "text/plain");
    }
}
