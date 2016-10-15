package com.jkys.moye;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by frio on 16/10/15.
 */
public class MoyeParser {
    private List<Word> words = new ArrayList<Word>();

    public List<Word> parseExpression(String expression) {
        char[] chars = expression.toCharArray();
        System.out.println(chars.length);
        for (int i = 0; i <= chars.length - 1; i++) {
            if(i == 6){
                System.out.println();
            }
            switch (chars[i]) {
                case ' ':
                    break;
                case '(':
                    words.add(new LeftBracket("("));
                    break;
                case ')':
                    words.add(new RightBracket(")"));
                    break;
                case '+':
                    words.add(new Operator(OperatorEnum.PLUS));
                    break;
                case '-':
                    words.add(new Operator(OperatorEnum.MINUS));
                    break;
                case '*':
                    words.add(new Operator(OperatorEnum.MULTIPLICATION));
                    break;
                case '/':
                    words.add(new Operator(OperatorEnum.DIVISION));
                    break;
                case '&':
                    i = i + 1;
                    if (chars[i] != '&') {
                        throw new ParseException(String.format("%d index char must be &", i));
                    }
                    words.add(new Operator(OperatorEnum.AND));
                    break;
                case '|':
                    i = i + 1;
                    if (chars[i] != '|') {
                        throw new ParseException(String.format("%d index char must be |", i));
                    }
                    words.add(new Operator(OperatorEnum.OR));
                    break;
                case '^':
                    words.add(new Operator(OperatorEnum.XOR));
                    break;
                case '>':
                    if (chars[i + 1] == '=') {
                        i = i + 1;
                        words.add(new Operator(OperatorEnum.GREATER_THAN_OR_EQUAL));
                    } else {
                        words.add(new Operator(OperatorEnum.GREATER_THAN));
                    }
                    break;
                case '<':
                    if (chars[i + 1] == '=') {
                        i = i + 1;
                        words.add(new Operator(OperatorEnum.LESS_THAN_OR_EQUAL));
                    } else {
                        words.add(new Operator(OperatorEnum.LESS_THAN));
                    }
                    break;
                case '\'':
                    throw new ParseException("not support \' ");
                case '=':
                    i = i + 1;
                    if (chars[i] != '=') {
                        throw new ParseException(String.format("%d index char must be =", i));
                    }
                    words.add(new Operator(OperatorEnum.EQUAL_EQUAL));
                    break;
                default:
                    Word word = subGroupExpression(chars, i);
                    if(word instanceof BaseTypeValue && ((BaseTypeValue) word).getValueType() == BaseTypeValue.ValueType.STRING) {
                        i = i + word.getName().length() + 1;
                    }else{
                        i = i + word.getName().length() - 1;
                    }
                    words.add(word);
            }
        }
        return words;
    }


    private Word subGroupExpression(char[] chars, int i) {
        StringBuilder sb = new StringBuilder();
        for (; i < chars.length ; i = i + 1) {
            if (chars[i] == ')' || chars[i] == ' ') {
                break;
            } else {
                sb.append(chars[i]);
            }
        }
        //in type
        if(sb.toString().equals(OperatorEnum.IN.getOperator())){
            return new Operator(OperatorEnum.IN);
        }
        //string type
        if (sb.toString().toCharArray()[0] == '"' && sb.toString().toCharArray()[sb.toString().length() - 1] != '"') {
            throw new ParseException(String.format("%d string is not end up with \"", i));
        } else if (sb.toString().toCharArray()[0] == '"') {
            return new BaseTypeValue(BaseTypeValue.ValueType.STRING, sb.toString().replace("\"", ""));
        }
        //int type
        if(isInteger(sb.toString())){
            return new BaseTypeValue(BaseTypeValue.ValueType.INT, sb.toString());
        }
        //double type
        if(isDouble(sb.toString())){
            return new BaseTypeValue(BaseTypeValue.ValueType.DOUBLE, sb.toString());
        }
        //array type
        if(isArray(sb.toString())){
            return new BaseTypeValue(BaseTypeValue.ValueType.ARRAY, sb.toString());
        }
        return new DynamicVariable(sb.toString());
    }

    private boolean isArray(String str) {
        Pattern pattern = Pattern.compile("^\\[.*?\\]$");
        return pattern.matcher(str).matches();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
