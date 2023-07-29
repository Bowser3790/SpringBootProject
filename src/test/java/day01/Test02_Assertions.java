package day01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test02_Assertions {

    // assertFalse/ assertTrue
    // assertTrue(true) ==> pass
    // assertFalse(false) ==> pass

    @Test
    void testToFindMin(){
        assertFalse(Math.min(23,134)!=134);
    }
    @Test
    void testArray(){
        String str = "Unit testing improves code quality";
        String [] actual = str.split(" ");
        String [] expectedValue = {"Unit","testing","improves", "code", "quality"};
        assertTrue(Arrays.equals(expectedValue,actual));
    }
}
