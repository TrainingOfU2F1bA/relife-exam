package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;
import com.tw.relife.test.SampleBadRequetException;
import com.tw.relife.test.SampleNotFoundException;

@RelifeController
public class ExceptionTestController {

    @RelifeRequestMapping(value = "/notFound", method = RelifeMethod.GET)
    public RelifeResponse NotFound(RelifeRequest request) {
        throw new SampleNotFoundException();
    }

    @RelifeRequestMapping(value = "/badRequest", method = RelifeMethod.GET)
    public RelifeResponse badReuest(RelifeRequest request) {
        throw new SampleBadRequetException();
    }

    @RelifeRequestMapping(value = "/normalException", method = RelifeMethod.GET)
    public RelifeResponse normalException(RelifeRequest request) {
        throw new IllegalStateException();
    }

}
