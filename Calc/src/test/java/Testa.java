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
    public void testPlusNegative() {
        String example = "-2+-2";
        assertEquals(Instance.eval(example), -4.0, 0.001);
    }
    @Test
    public void testMinusNegative() {
        String example = "-7--2";
        assertEquals(Instance.eval(example), -5.0, 0.001);
    }
    @Test
    public void testMultiplyNegative() {
        String example = "-3*3";
        assertEquals(Instance.eval(example), -9.0, 0.001);
    }
    @Test
    public void testDivideNegative() {
        String example = "-8/2";
        assertEquals(Instance.eval(example), -4.0, 0.001);
    }
    @Test
    public void testPowerNegative() {
        String example = "-2^3";
        assertEquals(Instance.eval(example), -8.0, 0.001);
    }

       @Test
        public void testBracers() {
            String example = "(2+(5-1))";
            assertEquals(Instance.eval(example), 6.0, 0.001);
        }

           @Test
            public void testBracersNegative() {
                String example = "-(-2+(7-1))";
                assertEquals(Instance.eval(example), -4.0, 0.001);
            }

    @Test
    public void Example1() {
        String example = "-3^(-5+-2*-(16/4))";
        assertEquals(Instance.eval(example), -27.0, 0.001);
    }

    @Test
    public void TooLittleBraces() {
        String example = "2+(3+2-(3+2)";
        assertEquals(Instance.checkString(example),false);

    }

    @Test
    public void TooManyBraces() {
        String example = "2+(3+2-(3+2)))";
        assertEquals(Instance.checkString(example),false);
    }

    @Test
    public void NoOperatorBeforeBraces() {
        String example = "2+(3+2(3+2))";
        assertEquals(Instance.checkString(example),false );
    }
    @Test
    public void NoDigitAsFirstSymbol() {
        String example = "*3";
        assertEquals(Instance.checkString(example),false);
    }
    @Test
    public void TwoOperatorsInARow() {
        String example = "2++3";
        assertEquals(Instance.eval(example), -1.0, 0.001);
    }
    @Test
    public void TwoPointsInOneDigit() {
        String example = "3.2+4..2";
        assertEquals(Instance.eval(example), -1.0, 0.001);
    }

    @Test
    public void DivideOnNull() {
        String example = "3/0";
        assertEquals(Instance.eval(example), -1.0, 0.001);
    }

    @Test
    public void NoOperatorAfterBrace() {
        String example = "2+(3*4)3";
        assertEquals(Instance.eval(example), -1.0, 0.001);
    }

    @Test
    public void FunctionSinus() {
        String example = "sin(90)";
        assertEquals(Instance.eval(example), Math.sin(90), 0.001);
    }

    @Test
    public void FunctionCosines() {
        String example = "cos(90)";
        assertEquals(Instance.eval(example), Math.cos(90), 0.001);
    }

}
