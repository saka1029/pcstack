package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;
import saka1029.pcstack.List;

public class TestQuote {

    @Test
    public void testExecuteInt() {
        Context c = Context.of();
        assertEquals(Int.of(3), c.eval("'3"));
        assertEquals(0, c.sp());
    }

    @Test
    public void testExecuteList() {
        Context c = Context.of();
        assertEquals(List.of(Int.of(1), Int.of(2)), c.eval("'(1 2)"));
        assertEquals(0, c.sp());
    }

}
