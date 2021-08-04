package com.demo.jexl;

import cn.hutool.core.util.StrUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateServiceImpl implements ICalculateService {

    @Override
    public Object execute(List<Object> list, String expression) throws CalculateException {
        try {
            var env = new HashMap<String, Object>(1);
            env.put(CalculateConstant.LIST, list);

            return AviatorEvaluator.execute(expression, env, true);
        } catch (Exception e) {
            throw new CalculateException("execute failed");
        }
    }

    @Override
    public List<String> sameCompare(double[][] list) throws CalculateException {
        AviatorEvaluator.defineFunction(CalculateConstant.SAME_COMPARE_LAMBDA, CalculateConstant.SAME_COMPARE_LAMBDA_EXPRESSION);
        AviatorEvaluator.addFunction(new SameCompareFunc());

        var env = new HashMap<String, Object>(1);
        env.put(CalculateConstant.LIST, list);

        var result = (Object[]) AviatorEvaluator.execute(StrUtil.format(CalculateConstant.LIST_MAP, CalculateConstant.SAME_COMPARE_FUNC), env, true);
        return Arrays.stream(result).map(String::valueOf).collect(Collectors.toList());
    }

    private static class SameCompareFunc extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env,
                                  AviatorObject arg1) {
            var param = FunctionUtils.getJavaObject(arg1, env);
            var map = new HashMap<String, Object>(1);
            map.put(CalculateConstant.PARAM, param);

            var result = AviatorEvaluator.execute(StrUtil.format("{}(param[0],param[1])", CalculateConstant.SAME_COMPARE_LAMBDA), map, true);
            return AviatorRuntimeJavaType.valueOf(result);
        }

        @Override
        public String getName() {
            return CalculateConstant.SAME_COMPARE_FUNC;
        }
    }
}
