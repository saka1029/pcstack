package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Context;

public class TestContext {
    
    @Test
    public void testArithmetics() {
        Context c = Context.of();
        assertEquals(c.eval("3"), c.eval("(1 2 +)"));
        assertEquals(c.eval("-1"), c.eval("(1 2 -)"));
        assertEquals(c.eval("6"), c.eval("(2 3 *)"));
        assertEquals(c.eval("1"), c.eval("(9 7 /)"));
        assertEquals(c.eval("2"), c.eval("(9 7 %)"));
    }
    
    @Test
    public void testComparison() {
        Context c = Context.of();
        assertEquals(c.eval("true"), c.eval("(2 2 ==)"));
        assertEquals(c.eval("false"), c.eval("(1 2 ==)"));
        assertEquals(c.eval("false"), c.eval("(2 2 !=)"));
        assertEquals(c.eval("true"), c.eval("(1 2 !=)"));
        assertEquals(c.eval("false"), c.eval("(2 2 <)"));
        assertEquals(c.eval("true"), c.eval("(1 2 <)"));
        assertEquals(c.eval("true"), c.eval("(2 2 <=)"));
        assertEquals(c.eval("true"), c.eval("(1 2 <=)"));
    }

    @Test
    public void testFor() {
        Context c = Context.of();
        assertEquals(c.eval("6"), c.eval("(0 '(1 2 3) '+ for)"));
    }
}
