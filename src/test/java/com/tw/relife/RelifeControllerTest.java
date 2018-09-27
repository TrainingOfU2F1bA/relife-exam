package com.tw.relife;

import com.tw.relife.controller.OneActionController;
import com.tw.relife.test.SampleBadRequetException;
import com.tw.relife.test.SampleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelifeControllerTest {
    @Test
    void should_can_handle_request_with_mutiple_request_path() {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
        .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello from /path",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }
}