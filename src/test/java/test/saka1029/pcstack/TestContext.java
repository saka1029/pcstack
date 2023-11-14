package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Generator;
import saka1029.pcstack.Int;
import saka1029.pcstack.List;
import saka1029.pcstack.Quote;
import saka1029.pcstack.Symbol;
import saka1029.pcstack.Verb;

public class TestContext {
    
    static Int i(int i) {
        return Int.of(i);
    }
    
    static Symbol sym(String name) {
        return Symbol.of(name);
    }
    
    static Quote q(Verb v) {
        return Quote.of(v);
    }

    static List list(Verb... elements) {
        return List.of(elements);
    }

    @Test
    public void testArithmetics() {
        Context c = Context.of(10);
        assertEquals(i(3), c.eval(list(i(1), i(2), sym("+"))));
        assertEquals(i(-1), c.eval(list(i(1), i(2), sym("-"))));
        assertEquals(i(6), c.eval(list(i(2), i(3), sym("*"))));
        assertEquals(i(1), c.eval(list(i(9), i(7), sym("/"))));
        assertEquals(i(2), c.eval(list(i(9), i(7), sym("%"))));
    }

    @Test
    public void testFor() {
        Context c = Context.of(10);
        // (0 '(1 2 3) '+ for)
        assertEquals(i(6), c.eval(list(i(0), q(list(i(1), i(2), i(3))), q(sym("+")), sym("for"))));
    }
    
    /**
     * 0 '(1 yield 2 yield) generator '+ for
     * -> 3
     */
    @Test
    public void testGenerator() {
        Context c = Context.of(10);
        assertEquals(i(3), c.eval(list(i(0), q(list(i(1), sym("yield"), i(2), sym("yield"))), sym("generator"), q(sym("+")), sym("for"))));
    }
}
