package com.demo.jexl;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateConstant {
    public final static String LIST = "list";
    public final static String PARAM = "param";

    public final static String LIST_SUM = StrUtil.format("reduce({},+,0)", LIST);
    public final static String LIST_SORT = StrUtil.format("sort({})", LIST);
    public final static String LIST_MAP = StrUtil.format("map({},{})", LIST);

    public final static String SAME_COMPARE_LAMBDA = "SameCompareLambda";
    public final static String SAME_COMPARE_LAMBDA_EXPRESSION = "lambda (x,y) -> (x/y)*100 end";
    public final static String SAME_COMPARE_FUNC = "SameCompareFunc";
}
