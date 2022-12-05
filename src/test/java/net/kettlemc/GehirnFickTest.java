package net.kettlemc;

import net.kettlemc.gehirnfick.BrainFuckInterpreter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GehirnFickTest {

    /**
     * Print an exclamation mark
     */
    @Test
    public void testCharacter() {
        String program = "+++++++++++++++++++++++++++++++++.";
        BrainFuckInterpreter interpreter = new BrainFuckInterpreter().setProgram(program).execute();
        assertEquals(interpreter.getOutput(), "!");
    }

    /**
     * Print the digit in the field as an ASCII char
     */
    @Test
    public void testDisplayNumber() {
        String program = "+++ > +++ +++ [ < ++++ ++++ > -] <.";
        BrainFuckInterpreter interpreter = new BrainFuckInterpreter().setProgram(program).execute();
        assertEquals(interpreter.getOutput(), "3");
    }

    /**
     * Print the whole band as ASCII chars
     */
    @Test
    public void testReadBand() {
        String program = "[.>]";
        BrainFuckInterpreter interpreter = new BrainFuckInterpreter().setProgram(program).setDefaultBand("abcd").execute();
        assertEquals(interpreter.getOutput(), "abcd");
    }

}
