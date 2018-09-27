package com.tw.relife;

import java.util.HashMap;

public class RelifeMvcHandlerBuilder {

    private RelifeAppHandlerImpl relifeAppHandler;
    private HashMap<ActionKey, RelifeAppHandler> handlers = new HashMap<>();

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
}
