package pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;
import saka1029.pcstack.List;

public class TestList {

    @Test
    public void testToString() {
        assertEquals("()", List.NIL.toString());
        assertEquals("(1 2)", List.of(Int.of(1), Int.of(2)).toString());
        assertEquals("(1 () 2)", List.of(Int.of(1), List.NIL, Int.of(2)).toString());
        assertEquals("(1 (0) 2)", List.of(Int.of(1), List.of(Int.of(0)), Int.of(2)).toString());
    }

    @Test
    public void testExecute() {
        Context c = Context.of(10);
        c.execute(List.of(Int.of(1), Int.of(2), List.of(Int.of(30), Int.of(40)), List.NIL, Int.of(5)));
        assertEquals(Int.of(5), c.pop());
        assertEquals(Int.of(40), c.pop());
        assertEquals(Int.of(30), c.pop());
        assertEquals(Int.of(2), c.pop());
        assertEquals(Int.of(1), c.pop());
        assertEquals(0, c.sp);
    }

}
