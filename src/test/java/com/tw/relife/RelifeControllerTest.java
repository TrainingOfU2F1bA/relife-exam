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


}