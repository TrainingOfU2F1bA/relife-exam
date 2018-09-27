package com.tw.relife;

import org.junit.jupiter.api.Test;

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
}