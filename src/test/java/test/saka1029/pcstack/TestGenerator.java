package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import saka1029.pcstack.Context;
import saka1029.pcstack.Int;

public class TestGenerator {

    @Test
    public void testSimple() {
        Context c = Context.of();
        assertEquals(Int.of(6), c.eval("0 '(1 yield 2 yield 3 yield) generator '+ for"));
    }
    
    @Test
    public void testFor() {
        Context c = Context.of();
        assertEquals(Int.of(6), c.eval("0 '(3 iota '(yield) for) generator '+ for"));
        assertEquals(Int.of(6), c.eval("0 '(3 iota 'yield for) generator '+ for"));
    }
    
    @Test
    public void testDefine() {
        Context c = Context.of();
        c.run("'('(iota '(yield) for) cons generator) 'gen define");
        assertEquals(Int.of(6), c.eval("0 3 gen '+ for"));
    }
    
    @Test
    public void testFactGenerator() {
        Context c = Context.of();
//        c.run("1 '(1 2 3 4 5 6) '(* @0 print) for");
        c.run("'(1 '(1 2 3 4 5 6) '(* @0 yield) for) generator 'print for");
    }
}
