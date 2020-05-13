package com.android.jetpack.zs;

/**
 * @author zhangshuai
 */
public class Calculator {
    public int evaluate(String expression){
        int sum=0;
        for (String s:expression.split("\\+")){
            sum+=Integer.valueOf(s);
        }
        return sum;
    }
}
