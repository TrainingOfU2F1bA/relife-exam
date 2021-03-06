package com.tw.relife;

import com.tw.relife.test.SampleBadRequetException;
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


    @Test
    void should_be_mapping_same_path_but_different_method_action() {
         RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"GET","text/plain"))
                .addAction("/path", RelifeMethod.POST, request -> new RelifeResponse(200,"POST","text/plain"))
        .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse studentResponse = app.process(
                new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(200,studentResponse.getStatus());
        assertEquals("POST",studentResponse.getContent());
        assertEquals("text/plain",studentResponse.getContentType());

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("GET",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }

    @Test
    void should_be_mapping_different_status_which_annotation_on_exception() {
          RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addAction("/path", RelifeMethod.GET, request -> {throw new SampleNotFoundException();})
                .addAction("/path", RelifeMethod.POST, request -> {throw new SampleBadRequetException();})
        .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse studentResponse = app.process(
                new RelifeRequest("/path", RelifeMethod.POST));

        assertEquals(400,studentResponse.getStatus());

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(404,response.getStatus());
    }
}