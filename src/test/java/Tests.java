import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    void testTest(){
        int[] numbers = {2, 3};
        int result = numbers[0] + numbers[1];
        assertEquals(6, result, "2 + 3 должно равняться 5");
    }

}
