package com.tw.relife;

import com.tw.relife.test.SampleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelifeAppExceptionAnnotationTest {
    @Test
    void should_can_return_status_code_which_be_annotation_on_exception() {
        RelifeApp app = new RelifeApp(request -> {
            throw new SampleNotFoundException("shoule return 404");
        });

        RelifeRequest whateverRequest = new RelifeRequest("/any/path", RelifeMethod.GET);
        RelifeResponse response = app.process(whateverRequest);

        assertEquals(404, response.getStatus());
    }
}
