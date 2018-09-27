package com.tw.relife;

import java.util.HashMap;

public class RelifeMvcHandlerBuilder {

    private RelifeAppHandlerImpl relifeAppHandler;

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod relifeMethod, RelifeAppHandler relifeAppHandler) {
        validateAction(path, relifeMethod, relifeAppHandler);
        ActionKey actionKey = new ActionKey(path, relifeMethod);
        this.relifeAppHandler = new RelifeAppHandlerImpl(actionKey, relifeAppHandler);
        return this;
    }

    private void validateAction(String path, RelifeMethod relifeMethod, RelifeAppHandler relifeAppHandler) {
        if (path == null || relifeMethod ==null || relifeAppHandler ==null) {
            throw new IllegalArgumentException("path/relifeMetho/reliderAppHandler should not be null");
        }
    }

    public RelifeAppHandler build() {
        return relifeAppHandler;
    }
}
