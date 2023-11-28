package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCPS {

    /**
     * 継続渡しスタイル - Wikipedia
     * https://ja.wikipedia.org/wiki/%E7%B6%99%E7%B6%9A%E6%B8%A1%E3%81%97%E3%82%B9%E3%82%BF%E3%82%A4%E3%83%AB#:~:text=%E7%B6%99%E7%B6%9A%E6%B8%A1%E3%81%97%E3%82%B9%E3%82%BF%E3%82%A4%E3%83%AB%20(CPS%3A%20Continuation,%E8%AB%96%E6%96%87%E3%81%AB%E3%81%8A%E3%81%84%E3%81%A6%E5%B0%8E%E5%85%A5%E3%81%95%E3%82%8C%E3%81%9F%E3%80%82
     * 
     * (define (factorial n)
     *     (f-aux n 1))
     * (define (f-aux n a)
     *     (if (= n 0)
     *         a
     *         (f-aux (- n 1) (* n a)))) ; 末尾再帰
     */
    static class ContinuationPassingStyle {
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
    }

    @Test
    public void testFactorial() {
        assertEquals(24, ContinuationPassingStyle.factorial(4));
        assertEquals(24, ContinuationPassingStyle.factorial_loop(4));
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

    /**
     * 引数の数ごとにインタフェースを定義する場合。
     */
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

    /**
     * 継続を最後の引数とする場合。
     */
    static class CPS0_1 {
        interface Cont { void apply(int a); } 
        interface C1 { void apply(int a, Cont c); } 
        interface C2 { void apply(int a, int b, Cont c); }

        static C1 inc_c = (a, c) -> c.apply(a + 1);
        static C2 add_c = (a, b, c) -> c.apply(a + b);

        static void execute() {
            add_c.apply(1, 3, v1 ->
                add_c.apply(v1, 5, v2 ->
                    inc_c.apply(v2, v3 ->
                        System.out.println(v3))));
        }
    }

    @Test
    public void testCSP0_1Execute() {
        CPS0_1.execute();   // -> 10
    }

    /**
     * 結果を戻り値として返す場合。
     */
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

    /**
     * 可変長引数のインタフェースを定義する場合。
     */
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
