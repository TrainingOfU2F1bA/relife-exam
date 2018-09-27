package com.tw.relife;

import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class RelifeMvcHandlerBuilder {

    private RelifeAppHandlerImpl relifeAppHandler;
    private HashMap<ActionKey, RelifeAppHandler> handlers = new HashMap<>();
    private Object controllerInstence;

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod relifeMethod, RelifeAppHandler relifeAppHandler) {
        validateAction(path, relifeMethod, relifeAppHandler);
        ActionKey actionKey = new ActionKey(path, relifeMethod);
        handlers.computeIfAbsent(actionKey,key -> relifeAppHandler);

        this.relifeAppHandler = new RelifeAppHandlerImpl(handlers);
        return this;
    }

    private void validateAction(String path, RelifeMethod relifeMethod, RelifeAppHandler relifeAppHandler) {
        if (path == null || relifeMethod ==null || relifeAppHandler ==null) {
            throw new IllegalArgumentException("path/relifeMetho/reliderAppHandler should not be null");
        }
    }

    public RelifeAppHandler build() {
        return relifeAppHandler == null ? request -> new RelifeResponse(404) : relifeAppHandler;
    }

    public RelifeMvcHandlerBuilder addController(Class<?> controller) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = validateContructor(controller);
        this.controllerInstence = constructor.newInstance();

        for (Method method : controller.getDeclaredMethods()) {
            RelifeRequestMapping annotation = method.getAnnotation(RelifeRequestMapping.class);
            if (annotation == null) continue;
            addAction(annotation.value(),annotation.method(),new RelifeControllerMethodHandler(controllerInstence,method));
        }

        return this;
    }

    private Constructor<?> validateContructor(Class<?> controller) throws NoSuchMethodException {

        if (controller == null) {
            throw new IllegalArgumentException("controller can not be null");
        }

        if (controller.isInterface()|| Modifier.isAbstract(controller.getModifiers())){
            throw new IllegalArgumentException(String.format("%s is abstract", controller.getName()));
        }

        if (!controller.isAnnotationPresent(RelifeController.class)) {
            throw new IllegalArgumentException("controller is not be RelifeController annotated");
        }

        Constructor<?> constructor = null;

        try {
            constructor = controller.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("%s has no default constructor", controller.getName()));
        }

        return constructor;
    }
}
