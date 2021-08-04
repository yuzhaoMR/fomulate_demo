package com.demo.jexl;

import cn.hutool.core.util.StrUtil;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Application {

    public static final String FORMULA_REGEX = "(?=\\[)(.*?])|\\d+(\\.\\d+)?|[+\\-*/%^()]";
    public static final String OPERATION_REGEX = "[+\\-*/%^()]";

    public static final String LEFT = "(";
    public static final String RIGHT = ")";
    public static final String ADD = "+";
    public static final String REDUCE = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";
    public static final String REMAINDER = "%";
    public static final String EXPONENT = "^";

    public static void main(String[] args) throws Exception {
//        convertToCode();

//        var str = "(1.4-1)*3-5/2%2^3*(2.1-1)";
        var str = "([1]-[2])*[3]-[4]/[5]%[6]^3*([7]-[8])";
        System.out.println(getResult(str));
    }

    private static String doubleCal(String num1, String num2, String operator) throws Exception {
        System.out.println(StrUtil.format("{} {} {}", num1, operator, num2));

        return StrUtil.format("{} {} {}", num1, operator, num2);
//        switch (operator) {
//            case ADD:
//                return num1 + num2;
//            case REDUCE:
//                return num1 - num2;
//            case MUL:
//                return num1 * num2;
//            case DIV:
//                return num1 / num2;
//            case REMAINDER:
//                return num1 % num2;
//            case EXPONENT:
//                return Math.pow(num1, num2);
//            default:
//                break;
//        }
//        throw new Exception("illegal operator!");
    }

    private static int getPriority(String s) throws Exception {
        if (s == null) {
            return 0;
        } else if (s.equals(LEFT)) {
            return 1;
        } else if (s.equals(ADD) || s.equals(REDUCE)) {
            return 2;
        } else if (s.equals(MUL) || s.equals(DIV)) {
            return 3;
        } else if (s.equals(REMAINDER)) {
            return 4;
        } else if (s.equals(EXPONENT)) {
            return 5;
        }

        throw new Exception("illegal operator!");
    }

    public static String getResult(String expr) throws Exception {
        var number = new LinkedList<String>();
        var operator = new LinkedList<String>();
        operator.push(null);

        var matcher = Pattern.compile(FORMULA_REGEX).matcher(expr);
        while (matcher.find()) {
            var temp = matcher.group();
            if (temp.matches(OPERATION_REGEX)) {
                if (temp.equals(LEFT)) {
                    operator.push(temp);
                } else if (temp.equals(RIGHT)) {
                    String tempOperator;
                    while (!(tempOperator = operator.pop()).equals(LEFT)) {
                        var num2 = number.pop();
                        var num1 = number.pop();
                        number.push(doubleCal(num1, num2, tempOperator));
                    }
                } else {
                    while (getPriority(temp) <= getPriority(operator.peek())) {
                        var num2 = number.pop();
                        var num1 = number.pop();
                        var tempOperator = operator.pop();
                        number.push(doubleCal(num1, num2, tempOperator));
                    }
                    operator.push(temp);
                }
            } else {
                number.push(String.valueOf(temp));
            }
        }

        while (operator.peek() != null) {
            var num2 = number.pop();
            var num1 = number.pop();
            var tempOperator = operator.pop();
            number.push(doubleCal(num1, num2, tempOperator));
        }
        var numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(number.pop());
    }

    private static void convertToCode() throws CalculateException {

        var imple = new CalculateServiceImpl();

        List<Object> x = Arrays.asList(1.1, 3.1, 2.1, 0.1);
        System.out.println(imple.execute(x, CalculateConstant.LIST_SUM));
        System.out.println(imple.execute(x, CalculateConstant.LIST_SORT));

        var list = new double[][]{{1, 10}, {2, 10}, {3, 10}, {4, 10}, {5, 10}};

        System.out.println(imple.sameCompare(list));
    }
}
