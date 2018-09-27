package com.tw.relife;

import com.tw.relife.*;
import com.tw.relife.controller.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelifeMultipleControllerTest {

    @Test
    void should_can_register_multiple_controllers() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionTestController.class)
                .addController(OneActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse badRequest = app.process(
                new RelifeRequest("/badRequest", RelifeMethod.GET));

        assertEquals(400,badRequest.getStatus());

        RelifeResponse notFound = app.process(
                new RelifeRequest("/notFound", RelifeMethod.GET));

        assertEquals(404,notFound.getStatus());

        RelifeResponse response = app.process(
                new RelifeRequest("/normalException", RelifeMethod.GET));

        assertEquals(500,response.getStatus());

        response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello from /path",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }

    @Test
    void should_use_first_mapping_after_register_multiple_controllers_which_includes_same_mapping() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .addController(OtherOneActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);

        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello from /path",response.getContent());
        assertEquals("text/plain",response.getContentType());
    }

    @Test
    void should_not_register_same_controller() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(OneActionController.class)
                .addController(OneActionController.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);
    }
}