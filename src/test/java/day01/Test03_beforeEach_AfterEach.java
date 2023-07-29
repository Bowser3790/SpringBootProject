package day01;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test03_beforeEach_AfterEach {

    /*
    https://chat.openai.com/share/ecb3bcb2-44bc-4119-aedd-501055489f19
    see link for chatGPT for explanation (row by row)
     */

    String str;

    @BeforeEach
    void beforeEach(){
        str = "Test methods should be simple";
        System.out.println("******** beforeEach() executed ********");

    }
    @AfterEach
    void afterEach(){
        str = "";
        System.out.println("******** afterEach() executed ********");
    }
    @Test
    void testArray(TestInfo info){
        String [] actual = str.split(" ");
        String [] expected = {"Test","methods","should", "be","simple"};
        assertTrue(Arrays.equals(expected,actual));
        System.out.println(info.getDisplayName()+ " executed");

    }
    @Test
    void testStringLength(TestInfo info){
        int actual = str.length();
        int expected = 29;
        assertEquals(expected,actual);
        System.out.println(info.getDisplayName()+ " executed");

    }
}
