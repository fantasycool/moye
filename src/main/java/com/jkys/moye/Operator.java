package com.jkys.moye;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by frio on 16/10/15.
 */
public class Operator extends Word {
    private OperatorEnum operatorEnum;

    public Operator(OperatorEnum operatorEnum) {
        super(operatorEnum.toString());
        this.operatorEnum = operatorEnum;
    }

    public static Object sum(Map<String, Object> context, Object... args) {
        BigDecimal sum = new BigDecimal(0);
        for (Object arg : args) {
            sum = sum.add(getValue(arg, context));
        }
        return sum;
    }

    public static Object minus(Map<String, Object> context, Object... args) {
        BigDecimal sum = new BigDecimal(0);
        int i = 0;
        for (Object arg : args) {
            if(i == 0){
                sum = sum.subtract(getValue(arg, context));
            }else{
                sum = sum.subtract(getValue(arg, context));
            }
            i = i + 1;
        }
        return sum;
    }

    public static Object multi(Map<String, Object> context, Object... args) {
        BigDecimal result = new BigDecimal(1);
        for (Object arg : args) {
            result = result.multiply(getValue(arg, context));
        }
        return result;
    }

    public static Object division(Map<String, Object> context, Object... args) {
        BigDecimal result = new BigDecimal(1);
        int i = 0;
        for (Object arg : args) {
            if (i == 0) {
                result = getValue(arg, context);
            } else {
                result = result.divide(getValue(arg, context));
            }
            i = i + 1;
        }
        return result;
    }

    public static Object and(Map<String, Object> context, Object... args) {
        int result = 1;
        for (Object arg : args) {
            result = result & getValue(arg, context).intValue();
        }
        return result;
    }

    public static Object or(Map<String, Object> context, Object... args) {
        int result = 1;
        for (Object arg : args) {
            result = result | getValue(arg, context).intValue();
        }
        return result;
    }

    public static Object xor(Map<String, Object> context, Object... args) {
        int result = 1;
        for (int i = 0; i < args.length; i ++) {
            if(i == 0){
                result = getValue(args[i], context).intValue();
            }else{
                result = result ^ getValue(args[i], context).intValue();
            }
        }
        return result;
    }

    public static Object greater(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("greater operator have to be 2 arguments"));
        }
        BigDecimal arg1 = getValue(args[0], context);
        BigDecimal arg2 = getValue(args[1], context);

        int compareResult = arg1.compareTo(arg2);
        if(compareResult <= 0){
            return new BigDecimal(0);
        }else{
            return new BigDecimal(1);
        }
    }

    public static Object greaterEqual(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("greater operator have to be 2 arguments"));
        }
        BigDecimal arg1 = getValue(args[0], context);
        BigDecimal arg2 = getValue(args[1], context);

        int compareResult = arg1.compareTo(arg2);
        if(compareResult < 0){
            return new BigDecimal(0);
        }else{
            return new BigDecimal(1);
        }
    }

    public static Object less(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("greater operator have to be 2 arguments"));
        }
        BigDecimal arg1 = getValue(args[0], context);
        BigDecimal arg2 = getValue(args[1], context);

        int compareResult = arg1.compareTo(arg2);
        if(compareResult < 0){
            return new BigDecimal(1);
        }else{
            return new BigDecimal(0);
        }
    }

    public static Object lessEqual(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("greater operator have to be 2 arguments"));
        }
        BigDecimal arg1 = getValue(args[0], context);
        BigDecimal arg2 = getValue(args[1], context);

        int compareResult = arg1.compareTo(arg2);
        if(compareResult <= 0){
            return new BigDecimal(1);
        }else{
            return new BigDecimal(0);
        }
    }

    public static Object equal(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("greater operator have to be 2 arguments"));
        }
        if(args[0] instanceof BaseTypeValue && args[1] instanceof BaseTypeValue){
            if(((BaseTypeValue)args[0]).getValueType() != ((BaseTypeValue)args[1]).getValueType()){
                return new BigDecimal(0l);
            }
        }
        String arg1 = getStrArg(args[0], context);
        String arg2 = getStrArg(args[1], context);
        if(arg1.equals(arg2)){
            return new BigDecimal(1);
        }else{
            return new BigDecimal(0);
        }
    }

    public static Object in(Map<String, Object> context, Object... args){
        if(args.length != 2){
            throw new ParseException(String.format("in operator have to be 2 arguments"));
        }
        String arg1 = getStrArg(args[0], context);
        if(args[1] instanceof BaseTypeValue){
            BaseTypeValue arg2 = (BaseTypeValue)args[1];
            if(arg2.getValueType() != BaseTypeValue.ValueType.ARRAY){
                throw new IllegalArgumentException("in operator second arg must be [] array");
            }else{
                String[] values = arg2.getName().replace("[","").replace("]","").split(",");
                for(String s : values){
                    if(s.replace("\"", "").trim().contains(arg1)){
                        return new BigDecimal(1);
                    }
                }
            }
        }else{
            throw new IllegalArgumentException("in operator second arg must be [] array");
        }
        return new BigDecimal(0);
    }

    private static String getStrArg(Object arg, Map<String, Object> context) {
        if (!(arg instanceof BaseTypeValue) && !(arg instanceof DynamicVariable) && !(arg instanceof BigDecimal)) {
            throw new ParseException("not valid arg:" + arg.toString());
        } else if (arg instanceof BaseTypeValue) {
            return ((BaseTypeValue) arg).getName().toString();
        } else if (arg instanceof DynamicVariable) {
            Object value = context.get(((Word) arg).getName());
            if (value == null) {
                throw new IllegalArgumentException("context does not contain key:" + ((Word) arg).getName());
            }
            if (!MoyeParserImpl.isDouble(value.toString()) && !MoyeParserImpl.isInteger(value.toString())) {
                throw new IllegalAccessError(String.format("context key:%s is not number value", ((Word) arg).getName()));
            }
            return value.toString();
        } else if(arg instanceof BigDecimal){
            return ((BigDecimal) arg).toPlainString();
        } else {
            return arg.toString();
        }
    }

    public static BigDecimal getValue(Object arg, Map<String,Object> context){
        if (!(arg instanceof BaseTypeValue) && !(arg instanceof DynamicVariable) && !(arg instanceof BigDecimal)) {
            throw new ParseException("not valid arg:" + arg.toString());
        } else if (arg instanceof BaseTypeValue && ((BaseTypeValue) arg).getValueType() == BaseTypeValue.ValueType.STRING) {
            throw new ParseException("string type value does not support string type");
        } else if (arg instanceof BaseTypeValue) {
            return new BigDecimal(((BaseTypeValue) arg).getName());
        } else if (arg instanceof DynamicVariable) {
            Object value = context.get(((Word) arg).getName());
            if (value == null) {
                throw new IllegalArgumentException("context does not contain key:" + ((Word) arg).getName());
            }
            if (!MoyeParserImpl.isDouble(value.toString()) && !MoyeParserImpl.isInteger(value.toString())) {
                throw new IllegalAccessError(String.format("context key:%s is not number value", ((Word) arg).getName()));
            }
            return new BigDecimal(value.toString());
        } else if(arg instanceof BigDecimal){
            return (BigDecimal) arg;
        } else {
            return new BigDecimal(arg.toString());
        }
    }

    public OperatorEnum getOperatorEnum() {
        return operatorEnum;
    }
}
