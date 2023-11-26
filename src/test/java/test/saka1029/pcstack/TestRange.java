package test.saka1029.pcstack;

import static org.junit.Assert.*;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;

public class TestRange {

    @Test
    public void testRange() {
        Context c = Context.of();
        assertEquals(Int.of(6), c.eval("0 1 3 range '+ for"));
        assertEquals(Int.of(6), c.eval("0 3 1 range '+ for"));
        assertEquals(Int.of(24), c.eval("1 1 4 range '* for"));
    }

    @Test
    public void testRangeStep() {
        Context c = Context.of();
        assertEquals(Int.of(6), c.eval("0 1 3 1 range-step '+ for"));
        assertEquals(Int.of(0), c.eval("0 1 3 -1 range-step '+ for"));
        c.run("'(1 swap 1 swap 1 range-step '* for) 'fact define");
        assertEquals(Int.of(1), c.eval("0 fact"));
        assertEquals(Int.of(1), c.eval("1 fact"));
        assertEquals(Int.of(2), c.eval("2 fact"));
        assertEquals(Int.of(6), c.eval("3 fact"));
        assertEquals(Int.of(24), c.eval("4 fact"));
        assertEquals(Int.of(120), c.eval("5 fact"));
    }

}
