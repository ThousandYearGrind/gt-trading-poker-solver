package org.poker;

public class Action {
    private String actionKey;

    public Action(String actionKey) {
        this.actionKey = actionKey;
    }

    @Override
    public String toString() {
        return this.actionKey;
    }

}
