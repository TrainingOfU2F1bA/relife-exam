package com.tw.relife;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.relife.controller.JsonSerilizeActionController;
import com.tw.relife.entity.Student;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerilizeContollerTest {
    @Test
    void should_can_handle_request_with_mutiple_request_path() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(JsonSerilizeActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        String tom = new ObjectMapper().writeValueAsString(new Student(1L, "Tom"));
        assertEquals(200,response.getStatus());
        assertEquals(tom,response.getContent());
        assertEquals("application/json",response.getContentType());
    }
}
