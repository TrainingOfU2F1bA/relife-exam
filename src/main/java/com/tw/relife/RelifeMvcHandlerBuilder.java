package com.tw.relife;

import java.util.HashMap;

public class RelifeMvcHandlerBuilder {

    private RelifeAppHandlerImpl relifeAppHandler;

    public RelifeMvcHandlerBuilder addAction(String path, RelifeMethod relifeMethod, RelifeAppHandler relifeAppHandler) {
        ActionKey actionKey = new ActionKey(path, relifeMethod);
        this.relifeAppHandler = new RelifeAppHandlerImpl(actionKey, relifeAppHandler);
        return this;
    }

    public RelifeAppHandler build() {
        return relifeAppHandler;
    }
}
