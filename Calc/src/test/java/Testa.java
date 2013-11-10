import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Testa {
    @Test
    public void testPlus() {
    String example = "2+2";
        assertEquals(Instance.eval(example), 4.0, 0.001);
    }
    @Test
    public void testMinus() {
        String example = "7-2";
        assertEquals(Instance.eval(example), 5.0, 0.001);
    }
    @Test
    public void testMultiply() {
        String example = "3*3";
        assertEquals(Instance.eval(example), 9.0, 0.001);
    }
    @Test
    public void testDivide() {
        String example = "8/2";
        assertEquals(Instance.eval(example), 4.0, 0.001);
    }
    @Test
    public void testPower() {
        String example = "4^2";
        assertEquals(Instance.eval(example), 16.0, 0.001);
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
