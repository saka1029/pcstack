package test.saka1029.pcstack;

import static org.junit.Assert.*;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;

public class TestPCStack {

    @Test
    public void testIf() {
        Context c = Context.of();
        assertEquals(Int.of(1), c.eval("true 1 2 if"));
        assertEquals(Int.of(2), c.eval("false 1 2 if"));
        assertEquals(Int.of(3), c.eval("1 2 true '+ '- if"));
        assertEquals(Int.of(-1), c.eval("1 2 false '+ '- if"));
    }

    @Test
    public void testFact() {
        Context c = Context.of();
        c.run("'(@0 0 <= '(drop 1) '(@0 1 - fact *) if) 'fact define");
        assertEquals(Int.of(1), c.eval("0 fact"));
        assertEquals(Int.of(1), c.eval("1 fact"));
        assertEquals(Int.of(2), c.eval("2 fact"));
        assertEquals(Int.of(6), c.eval("3 fact"));
        assertEquals(Int.of(24), c.eval("4 fact"));
        assertEquals(Int.of(120), c.eval("5 fact"));
    }

}
