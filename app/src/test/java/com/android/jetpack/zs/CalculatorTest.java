package com.android.jetpack.zs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private String total;
    private int result;

    /**
     * 在类中所有方法前运行。此注解修饰的方法必须是static void
     */
    @BeforeClass
    public static void startClass(){
        System.out.print("BeforeClass 。。。。\n");
    }



    /**
     * 在每个测试方法前执行，可做初始化操作
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        System.out.print("Before 。。。。\n");
        total="1+2+3";
        result=6;
    }



    /**
     * 表示此方法为测试方法
     */
    @Test
    public void evaluate() {
        System.out.print("Test 。。。。\n");

        Calculator calculator=new Calculator();
        int sum=calculator.evaluate(total);
        assertEquals(result,sum);
    }


    /**
     * 在每个测试方法后执行，可做释放资源操作
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.print("After 。。。。\n");

    }

    /**
     * 忽略的测试方法
     * @throws Exception
     */
    @Ignore
    public void testIgnore()throws Exception {
        System.out.print("Ignore 。。。。\n");

    }


    /**
     * 在类中最后运行。此注解修饰的方法必须是static void
     */
    @AfterClass
    public static void afterClass(){
        System.out.print("AfterClass 。。。。\n");
    }
}