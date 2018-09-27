package com.tw.relife.controller;

import com.tw.relife.RelifeMethod;
import com.tw.relife.RelifeRequest;
import com.tw.relife.RelifeResponse;
import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;
import com.tw.relife.entity.Student;

@RelifeController
public class JsonSerilizeActionController {
    @RelifeRequestMapping(value = "/path", method = RelifeMethod.GET)
    public Student sayHello(RelifeRequest request) {
        return new Student(1L,"Tom");
    }
}
