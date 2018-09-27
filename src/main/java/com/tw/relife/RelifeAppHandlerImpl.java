package com.tw.relife;

import javax.swing.*;

public class RelifeAppHandlerImpl extends RelifeApp{
    private ActionKey actionKey;

    public RelifeAppHandlerImpl(ActionKey actionKey, RelifeAppHandler relifeAppHandler) {
        super(relifeAppHandler);
        this.actionKey = actionKey;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        ActionKey key = new ActionKey(request.getPath(), request.getMethod());
        if (key.equals(actionKey)){
            RelifeResponse response = super.process(request);
            return response == null ? new RelifeResponse(200) : response;
        }
        return new RelifeResponse(404);
    }
}
