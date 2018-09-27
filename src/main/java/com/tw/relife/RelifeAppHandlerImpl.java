package com.tw.relife;

public class RelifeAppHandlerImpl implements RelifeAppHandler{
    private ActionKey actionKey;
    private RelifeAppHandler relifeAppHandler;

    public RelifeAppHandlerImpl(ActionKey actionKey, RelifeAppHandler relifeAppHandler) {
        this.actionKey = actionKey;
        this.relifeAppHandler = relifeAppHandler;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) {
        ActionKey key = new ActionKey(request.getPath(), request.getMethod());
        if (key.equals(actionKey)){
            RelifeResponse response = relifeAppHandler.process(request);
            return response == null ? new RelifeResponse(200) : response;
        }
        return new RelifeResponse(404);
    }
}
