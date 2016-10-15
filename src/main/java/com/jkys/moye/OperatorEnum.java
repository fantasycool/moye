package com.jkys.moye;

/**
 * Created by frio on 16/10/15.
 */
public enum OperatorEnum {
    GREATER_THAN(">"), // `>`
    LESS_THAN("<"), // `<`
    GREATER_THAN_OR_EQUAL(">="),// `<`
    LESS_THAN_OR_EQUAL("<="),// `<`
    EQUAL_EQUAL("=="),// `==`
    PLUS("+"),// `+`
    MINUS("-"),// `-`
    MULTIPLICATION("*"),// `*`
    DIVISION("/"),// `<`
    AND("&&"),// `&&`
    OR("||"),// `||`
    IN("in"), //`in`
    XOR("^"); // `^`

    private String operator;

    OperatorEnum(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
