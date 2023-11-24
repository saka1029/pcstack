package test.saka1029.pcstack;

import static org.junit.Assert.*;

import org.junit.Test;

import saka1029.pcstack.Context;

public class TestPCStack {

    @Test
    public void testAdd() {
        Context c = Context.of(10);
        assertEquals(c.eval("3"), c.eval("1 2 +"));
    }

    @Test
    public void testIf() {
        Context c = Context.of(10);
        assertEquals(c.eval("1"), c.eval("true 1 2 if"));
        assertEquals(c.eval("2"), c.eval("false 1 2 if"));
        assertEquals(c.eval("3"), c.eval("1 2 true '+ '- if"));
        assertEquals(c.eval("-1"), c.eval("1 2 false '+ '- if"));
    }
    
    @Test
    public void testDefine() {
        Context c = Context.of(10);
        c.run("3 'three define");
        assertEquals(c.eval("3"), c.eval("three"));
        c.run("'+ 'plus define");
        assertEquals(c.eval("3"), c.eval("1 2 plus"));
        c.run("'(+) 'plus2 define");
        assertEquals(c.eval("3"), c.eval("1 2 plus2"));
    }

    @Test
    public void testFact() {
        Context c = Context.of(30);
        c.run("'(@0 0 <= '(drop 1) '(@0 1 - fact *) if) 'fact define");
        assertEquals(c.eval("1"), c.eval("0 fact"));
        assertEquals(c.eval("1"), c.eval("1 fact"));
        assertEquals(c.eval("2"), c.eval("2 fact"));
        assertEquals(c.eval("6"), c.eval("3 fact"));
        assertEquals(c.eval("24"), c.eval("4 fact"));
    }
}
