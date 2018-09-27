package com.tw.relife;

import com.tw.relife.controller.*;
import com.tw.relife.test.SampleBadRequetException;
import com.tw.relife.test.SampleNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelifeControllerTest {
    @Test
    void should_can_register_one_action_use_by_an_controller() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_controller_without_defalut_construct() {
       Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithoutDefaultConstructor.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_abstract_class() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(AbstractController.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_interface() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerTestInterface.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_null() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(null)
                .build();

        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_controller_without_relifeController_annotation() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithoutRelifeControllerAnnotation.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_controller_with_relifeController_with_a_param_which_type_is_not_relifeRequest() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithOneStringParamMethod.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);

    }


    @Test
    void should_throw_illegalArugementExcepton_when_register_a_controller_with_relifeController_with_multiple_param() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithTwoParamMethod.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void should_throw_illegalArugementExcepton_when_register_a_controller_with_relifeController_with_wrong_return_type() {
        Executable executable = () -> new RelifeMvcHandlerBuilder()
                .addController(ControllerWithWrongReturnType.class)
                .build();

        assertThrows(IllegalArgumentException.class, executable);

    }

    @Test
    void should_can_register_one_action_use_by_an_controller_which_with_multiple_action() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(MutipleActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello from /path",response.getContent());
        assertEquals("text/plain",response.getContentType());

        RelifeResponse hiResponse = app.process(
                new RelifeRequest("/hi", RelifeMethod.GET));

        assertEquals(200,hiResponse.getStatus());
        assertEquals("Hi from /hi",hiResponse.getContent());
        assertEquals("text/plain",hiResponse.getContentType());
    }

    @Test
    void should_mapping_same_action_when_register_controller_and_multiple_same_mapping_be_register() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(MutipleSameActionController.class)
                .build();

        RelifeApp app = new RelifeApp(handler);
        RelifeResponse response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hi from /path",response.getContent());
        assertEquals("text/plain",response.getContentType());

        RelifeResponse sameResponse = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,sameResponse.getStatus());
        assertEquals("Hi from /path",sameResponse.getContent());
        assertEquals("text/plain",sameResponse.getContentType());
    }

    @Test
    void should_be_mapping_different_status_which_annotation_on_exception() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionTestController.class)
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
    }


    @Test
    void should_be_register_mapping_mix_controller_and_action() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RelifeAppHandler handler = new RelifeMvcHandlerBuilder()
                .addController(ExceptionTestController.class)
                .addAction("/path", RelifeMethod.GET, request -> new RelifeResponse(200,"Hello","text/plain"))
                .addAction("/student", RelifeMethod.GET, request -> new RelifeResponse(200,"Student","text/plain"))
                .addAction("/badRequest",RelifeMethod.GET,request -> {throw new SampleNotFoundException();})
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

        RelifeResponse studentResponse = app.process(
                new RelifeRequest("/student", RelifeMethod.GET));

        assertEquals(200,studentResponse.getStatus());
        assertEquals("Student",studentResponse.getContent());
        assertEquals("text/plain",studentResponse.getContentType());

        response = app.process(
                new RelifeRequest("/path", RelifeMethod.GET));

        assertEquals(200,response.getStatus());
        assertEquals("Hello",response.getContent());
        assertEquals("text/plain",response.getContentType());

    }
}