import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Testa {
    @Test
    public void testAdd() {
    String example = "2+2";
        assertEquals(Instance.eval(example), 4.0, 0.001);
    }

       @Test
        public void testBracers() {
            String example = "(2+(5-1))";
            assertEquals(Instance.eval(example), 6.0, 0.001);
        }

           @Test
            public void testNegative() {
                String example = "(-2+(5-1))";
                assertEquals(Instance.eval(example), 2.0, 0.001);
            }

}
