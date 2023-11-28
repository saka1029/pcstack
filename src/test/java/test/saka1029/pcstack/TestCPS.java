package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TestCPS {

    interface F {
        Object[] apply(Object... args);
    }

    interface C {
        F apply(F f);
    }

    static Object[] reverse(Object... args) {
        int size = args.length;
        Object[] result = new Object[size];
        for (int i = 0, j = size - 1; i < size; ++i, --j)
            result[i] = args[j];
        return result;
    }

    static Object car(Object... args) {
        return args[0];
    }

    static Object[] cdr(Object... args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    /**
     * (define (cps-prim f) (lambda args (let ((r (reverse args))) ((car r) (apply f
     * (reverse (cdr r)))))))
     */
    static C csp_prim = (F f) -> (Object... args) -> {
        Object[] r = reverse(args);
        return ((F) r[0]).apply(f.apply(reverse(cdr(r))));
    };

    /**
     * (define (factorial n) (f-aux n 1)) (define (f-aux n a) (if (= n 0) a (f-aux
     * (- n 1) (* n a)))) ; 末尾再帰
     */
    static int factorial(int n) {
        return f_aux(n, 1);
    }

    static int f_aux(int n, int a) {
        return n == 0
            ? a
            : f_aux(n - 1, n * a);
    }

    static int factorial_loop(int n) {
        return f_loop(n, 1);
    }

    static int f_loop(int n, int a) {
        while (n > 0)
            a *= n--;
        return a;
    }

    @Test
    public void testFactorial() {
        assertEquals(24, factorial(4));
        assertEquals(24, factorial_loop(4));
    }

    /*
     * CPS(継続渡しスタイル)のプログラミングに入門する #継続 - Qiita
     * https://qiita.com/Kai101/items/eae3a00fcd1fc87e25fb
     * 
     * def inc_c(cont, x): cont(x+1)
     * 
     * def add_c(cont, x, y): cont(x+y)
     * 
     * def execute(): add_c( lambda v1: add_c( lambda v2: inc_c( lambda v3:
     * print(v3) , v2) , v1, 5), 1, 3)
     * 
     * execute()
     */
    static class NormalStyle {
        static int inc(int a) { return a + 1; }
        static int add(int a, int b) { return a + b; }

        static void execute() {
            System.out.println(inc(add(add(1, 3), 5)));
        }
    }
    
    @Test
    public void testNormalStyle() {
        NormalStyle.execute();  // -> 10
    }

    static class CPS0 {
        interface Cont { void apply(int a); } 
        interface C1 { void apply(Cont c, int a); } 
        interface C2 { void apply(Cont c, int a, int b); }

        static C1 inc_c = (c, a) -> c.apply(a + 1);
        static C2 add_c = (c, a, b) -> c.apply(a + b);

        static void execute() {
            add_c.apply(v1 ->
                add_c.apply(v2 ->
                    inc_c.apply(v3 ->
                        System.out.println(v3),
                        v2),
                    v1, 5),
                1, 3);
        }
    }

    @Test
    public void testS0Execute() {
        CPS0.execute();   // -> 10
    }

    static class CPS1 {
        interface Cont { void apply(int a); } 
        interface C1 { void apply(Cont c, int a); } 
        interface C2 { void apply(Cont c, int a, int b); }

        static C1 inc_c = (c, a) -> c.apply(a + 1);
        static C2 add_c = (c, a, b) -> c.apply(a + b);

        static int execute() {
            int[] result = {0};
            add_c.apply(v1 ->
                add_c.apply(v2 ->
                    inc_c.apply(v3 ->
                        result[0] = v3,
                        v2),
                    v1, 5),
                1, 3);
            return result[0];
        }
    }

    @Test
    public void testS1Execute() {
        assertEquals(10, CPS1.execute());
    }

    static class CPS2 {
        interface Cont { void apply(int a); } 
        interface F { void apply(Cont c, int... args); } 

        static F inc_c = (c, args) -> c.apply(args[0] + 1);
        static F add_c = (c, args) -> c.apply(args[0] + args[1]);

        static int execute() {
            int[] result = {0};
            add_c.apply(v1 ->
                add_c.apply(v2 ->
                    inc_c.apply(v3 ->
                        result[0] = v3,
                        v2),
                    v1, 5),
                1, 3);
            return result[0];
        }
    }

    @Test
    public void testS2Execute() {
        assertEquals(10, CPS2.execute());
    }

}
