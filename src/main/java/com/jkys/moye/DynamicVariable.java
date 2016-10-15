package com.jkys.moye;

/**
 * Created by frio on 16/10/15.
 */
public class DynamicVariable extends Word{
    private Object value;

    public DynamicVariable(String name) {
        super(name);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
