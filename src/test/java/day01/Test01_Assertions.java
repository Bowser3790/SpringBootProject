package day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class Test01_Assertions {
    @Test // makes method runnable
    public void test(){

    }

    // starting with Junit5, "public" access modifier is optional
    // it is suggested that test name should start with test

    // test length method from java
    @Test
    public void testLength(){
        String str = "Hello World";
        int actualValue = str.length();
        int expectedValue = 11;
        assertEquals(expectedValue,actualValue);
    }
    //test uppercase method from core java
    @Test
    public void testUpperCase(){
        String actualValue = "hello".toUpperCase();
        String expectedValue = "HELLO";
        assertEquals(expectedValue,actualValue,"uppercase method failed");
    }
    // test Math.addExact from core java
    @Test
    public void testAddExact(){
        int a= 4, b=5;
        int actualValue = Math.addExact(a, b);
        int expectedValue = 9;
        assertEquals(expectedValue,actualValue, "The numbers are not adding correctly");

    }
    @Test
    void testContains(){
        assertEquals(false, "JUnit".contains("z"));

    }

}
