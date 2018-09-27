package com.tw.relife;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class RelifeMvcHandlerBuilderTest {
    @Test
    void should_can_handle_request_with_request_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"Hello","text/plain"))
        .build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }

    @Test
    void should_throw_illgegalArgumentException_when_one_parameter_of_addAction_is_null() {
        Executable executable = () ->new RelifeMvcHandlerBuilder()
                .addAction("/path", null, request -> new RelifeResponse(200,"Hello","text/plain"));

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_response_200_when_relifeAppHandler_process_return_null() {

    }
}