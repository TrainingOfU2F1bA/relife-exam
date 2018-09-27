package com.tw.relife;

public class ActionKey {

    private String path;
    private RelifeMethod relifeMethod;

    public ActionKey(String path, RelifeMethod relifeMethod) {
        this.path = path;
        this.relifeMethod = relifeMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionKey actionKey = (ActionKey) o;

        if (path != null ? !path.equals(actionKey.path) : actionKey.path != null) return false;
        return relifeMethod == actionKey.relifeMethod;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (relifeMethod != null ? relifeMethod.hashCode() : 0);
        return result;
    }
}
