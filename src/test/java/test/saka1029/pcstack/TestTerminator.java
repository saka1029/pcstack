package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Terminator;

public class TestTerminator {
    
    @Test
    public void testToString() {
        assertEquals("yield", Terminator.YIELD.toString());
    }

}
