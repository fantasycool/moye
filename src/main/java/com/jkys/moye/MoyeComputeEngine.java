package com.jkys.moye;

import java.util.Map;

/**
 * Created by frio on 16/10/15.
 */
public interface MoyeComputeEngine {

    Object execute(String expression, Map<String, Object> context);
}
