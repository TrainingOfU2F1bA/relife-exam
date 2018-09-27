package com.tw.relife;

import com.tw.relife.annotation.RelifeController;
import com.tw.relife.annotation.RelifeRequestMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RelifeMvcHandlerBuilder {

    private RelifeAppHandlerImpl relifeAppHandler;
    private HashMap<ActionKey, RelifeAppHandler> handlers = new HashMap<>();
    private Object controllerInstence;
    private List<Class<?>> classes =new ArrayList<>();

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
        if (classes.contains(controller)) {
           throw new IllegalArgumentException("The controller has been register");
        }
        classes.add(controller);
        this.controllerInstence = constructor.newInstance();

        Arrays.stream(controller.getDeclaredMethods()).sorted(RelifeMvcHandlerBuilder::compareMethods).forEach(method -> {
            RelifeRequestMapping annotation = method.getAnnotation(RelifeRequestMapping.class);
            if (annotation != null) {
                validateAction(method);
                addAction(annotation.value(), annotation.method(), new RelifeControllerMethodHandler(controllerInstence, method));
            }
        });

        return this;
    }

    private void validateAction(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!(parameterTypes.length == 1 && RelifeRequest.class.equals(parameterTypes[0]))) {
            throw new IllegalArgumentException("Wrong action method with wrong parameter list");
        }

//        if (!RelifeResponse.class.equals(method.getReturnType())) {
//            throw new IllegalArgumentException("Wrong action method with wrong return type");
//        }
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

    private static int compareMethods(Method method1, Method method2) {
        int result = method1.getName().compareTo(method2.getName());
        if (result == 0) result = method1.getParameterCount() - method2.getParameterCount();
        if (result == 0 ){
            for (int i = 0; i < method1.getParameterTypes().length; i++) {
                result = method2.getParameterTypes()[i].getName().compareTo(method1.getGenericParameterTypes()[i].getTypeName());
                if (result != 0) {
                    break;
                }
            }
        }
        return result;
    }
}
