package com.tw.relife;

import com.tw.relife.test.SampleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelifeMvcMutipleHandlerBuilderTest {
    @Test
    void should_can_handle_request_with_mutiple_request_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"Hello","text/plain"))
                .addAction("/student", RelifeMethod.GET, request -> new RelifeResponse(200,"Student","text/plain"))
        .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse studentResponse = app.process(
                new RelifeRequest("/student", RelifeMethod.GET));

        assertEquals(200,studentResponse.getStatus());
        assertEquals("Student",studentResponse.getContent());
        assertEquals("text/plain",studentResponse.getContentType());

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }

    @Test
    void should_be_mapping_first_handler_after_register_same_action() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"Hello","text/plain"))
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"Second","text/plain"))
                .build();
        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
    }
}