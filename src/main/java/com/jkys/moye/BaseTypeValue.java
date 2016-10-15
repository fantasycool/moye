package com.jkys.moye;

/**
 * Created by frio on 16/10/15.
 */
public class BaseTypeValue extends Word {
    private ValueType valueType;

    public BaseTypeValue(ValueType valueType, String name){
        super(name);
        this.valueType = valueType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public enum ValueType{
        INT, DOUBLE, STRING, ARRAY
    }

}
