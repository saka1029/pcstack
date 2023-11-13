package pcstack;

import static org.junit.Assert.*;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;
import saka1029.pcstack.List;
import saka1029.pcstack.Quote;

public class TestQuote {

    @Test
    public void testExecuteInt() {
        Context c = Context.of(3);
        c.execute(Quote.of(Int.of(3)));
        assertEquals(Int.of(3), c.pop());
        assertEquals(0, c.sp);
    }

    @Test
    public void testExecuteList() {
        Context c = Context.of(3);
        c.execute(Quote.of(List.of(Int.of(1), Int.of(2))));
        assertEquals(List.of(Int.of(1), Int.of(2)), c.pop());
        assertEquals(0, c.sp);
    }

}
