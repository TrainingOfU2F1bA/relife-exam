package com.tw.relife;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RelifeAppHandlerImpl implements RelifeAppHandler{
    private Map<ActionKey, RelifeAppHandler> handlers;

    public RelifeAppHandlerImpl(Map<ActionKey, RelifeAppHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public RelifeResponse process(RelifeRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        ActionKey key = new ActionKey(request.getPath(), request.getMethod());
        if (handlers.containsKey(key)){
            RelifeResponse response = handlers.get(key).process(request);
            return response == null ? new RelifeResponse(200) : response;
        }
        return new RelifeResponse(404);
    }
}
