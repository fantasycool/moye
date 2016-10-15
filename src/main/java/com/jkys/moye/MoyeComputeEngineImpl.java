package com.jkys.moye;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by frio on 16/10/15.
 */
public class MoyeComputeEngineImpl implements MoyeComputeEngine {

    public Object execute(String expression, Map<String, Object> context) {
        MoyeParser moyeParser = new MoyeParser();
        List<Word> words = moyeParser.parseExpression(expression);
        for(Word w : words){
            System.out.println(w.getName());
        }
        return execute(words, context);
    }

    public Object execute(List<Word> words, Map<String, Object> context) {
        boolean isLeft = false;
        Operator operator = null;
        List<Object> args = new ArrayList<Object>();
        for (int i = 0; i < words.size() - 1; i++) {
            if (i == 0 && !(words.get(i) instanceof LeftBracket)) {
                throw new ParseException("expression must be started with (");
            } else if (words.get(i) instanceof Operator) {
                operator = (Operator) words.get(i);
            } else if (words.get(i) instanceof BaseTypeValue || words.get(i) instanceof DynamicVariable) {
                args.add(words.get(i));
            } else if (words.get(i) instanceof LeftBracket && isLeft) {
                List<Word> groupWords = cutoutGroupWords(words, i);
                args.add(execute(groupWords, context));
                i = i + groupWords.size() - 1;
            } else if (words.get(i) instanceof LeftBracket) {
                isLeft = true;
            }
        }
        if (operator == null) {
            throw new ParseException("no operator found in expression");
        }
        if (operator.getOperatorEnum() == OperatorEnum.PLUS) {
            return Operator.sum(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.AND) {
            return Operator.and(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.OR) {
            return Operator.or(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.MULTIPLICATION) {
            return Operator.multi(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.DIVISION) {
            return Operator.division(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.MINUS) {
            return Operator.minus(context, args.toArray());
        } else if (operator.getOperatorEnum() == OperatorEnum.XOR) {
            return Operator.xor(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.EQUAL_EQUAL){
            return Operator.equal(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.GREATER_THAN){
            return Operator.greater(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.GREATER_THAN_OR_EQUAL){
            return Operator.greaterEqual(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.LESS_THAN){
            return Operator.less(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.LESS_THAN_OR_EQUAL){
            return Operator.lessEqual(context, args.toArray());
        } else if( operator.getOperatorEnum() == OperatorEnum.IN){
            return Operator.in(context, args.toArray());
        }
        return null;
    }

    private List<Word> cutoutGroupWords(List<Word> words, int i) {
        List<Word> args = new ArrayList<Word>();
        int leftBracketSum = 0;
        for (; i < words.size(); i++) {
            if (words.get(i) instanceof RightBracket) {
                args.add(words.get(i));
                leftBracketSum = leftBracketSum - 1;
                if (leftBracketSum == 0) {
                    break;
                }
            } else if (words.get(i) instanceof LeftBracket) {
                leftBracketSum = leftBracketSum + 1;
                args.add(words.get(i));
            } else {
                args.add(words.get(i));
            }
        }
        return args;
    }

}
