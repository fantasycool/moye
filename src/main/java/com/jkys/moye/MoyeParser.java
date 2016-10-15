package com.jkys.moye;

import java.util.List;

/**
 * Created by frio on 16/10/15.
 */
public interface MoyeParser {

    List<Word> parseExpression(String expression);
}
