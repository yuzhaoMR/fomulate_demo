package com.demo.jexl;

import java.util.List;

public interface ICalculateService {
    public abstract Object execute(List<Object> list, String expression) throws CalculateException;

    public abstract List<String> sameCompare(double[][] list) throws CalculateException;
}
