package com.android.jetpack.zs;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @BeforeClass
    public static void start(){
        System.out.print("Start running BeforeClass from ExampleUnitTest\n");
    }

    @Test
    public void addition_isCorrect() {
        System.out.print("ExampleUnitTest : 哈哈哈");
        assertEquals(4, 2 + 2);
    }
}