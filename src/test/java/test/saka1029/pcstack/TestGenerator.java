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
        assertEquals(Int.of(6), c.eval("0 '(3 iota '(yield) for) generator '+ for"));
    }
    
    @Test
    public void testDefine() {
        Context c = Context.of();
        c.run("'('(iota '(yield) for) cons generator) 'gen define");
        assertEquals(Int.of(6), c.eval("0 3 gen '+ for"));
    }
    
    /**
     * generatorの問題ではなく、forの問題？
     * 
     *         add("for", c -> {
     *             Verb closure = c.pop();
     *             for (Verb e : (Collection)c.pop()) {
     *                 c.push(e);
     *                 c.execute(closure);
     *             }
     *             output(c);
     *         });
     * 
     * c.execute(closure)を繰返すので、stackにyieldが溜まってしまう。
     * forを実行した後のstackは
     * [1, yield, 2, yield, 3, yield]
     * となっている。
     * c.execute(closure)をc.executeAsList(closure)に変更するとうまくいく。
     */
    @Test
    public void testFor() {
        Context c = Context.of();
        assertEquals(Int.of(6), c.eval("0 '(3 iota 'yield for) generator '+ for"));
    }

}
