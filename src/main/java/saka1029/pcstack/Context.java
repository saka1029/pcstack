package saka1029.pcstack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Context {

    final java.util.List<Verb> stack;

    final Deque<List> rstack;
    final Map<Symbol, Verb> globals;
    final Consumer<String> output;

    Context(java.util.List<Verb> stack, Deque<List> rstack, Map<Symbol, Verb> globals, Consumer<String> output) {
        this.stack = stack;
        this.rstack = rstack;
        this.globals = globals;
        this.output = output;
    }

    Context() {
        this(new ArrayList<>(), new ArrayDeque<>(), new HashMap<>(), System.out::println);
        init();
    }
    
    public static Context of() {
        return new Context();
    }
    
    public Context child() {
        return new Context(new ArrayList<>(), new ArrayDeque<>(), globals, output);
    }
    
    public int sp() {
        return stack.size();
    }

    public Verb pop() {
        return stack.removeLast();
    }

    public void push(Verb v) {
        stack.add(v);
    }
    
    public Verb peek(int index) {
        return stack.get(sp() - index -1);
    }

    public void dup(int index) {
        push(peek(index));
    }
    
    public void drop() {
        stack.removeLast();
    }
    
    public void swap() {
        int top = sp() - 1, second = top - 1;
        Verb temp = stack.get(top);
        stack.set(top, stack.get(second));
        stack.set(second, temp);
    }
    
    public void output(Verb v) {
        if (output != null)
            output.accept(v.toString());
    }
    
    public void execute(Verb v) {
        v.execute(this);
    }
    
    public Verb eval(Verb v) {
        int p = sp();
        execute(v);
        assert sp() == p + 1 : "sp current=%d previous=%d".formatted(sp(), p);
        return pop();
    }
    
    @Override
    public String toString() {
        return stack.toString();
    }
    
    void add(String name, Verb v) {
        globals.put(Symbol.of(name), v);
    }
    
    static int i(Verb v) {
        return ((Int)v).value;
    }
    
    static Int i(int i) {
        return Int.of(i);
    }
    
    static boolean b(Verb v) {
        return ((Bool)v).value;
    }
    
    static Bool b(boolean b) {
        return Bool.of(b);
    }
    
    static Ordered o(Verb v) {
        return (Ordered)v;
    }

    void init() {
        add("@0", c -> dup(0));
        add("@1", c -> dup(1));
        add("drop", Context::drop);
        add("swap", Context::swap);
        add("+", c -> c.push(i(i(c.pop()) + i(c.pop()))));
        add("-", c -> c.push(i(-i(c.pop()) + i(c.pop()))));
        add("*", c -> c.push(i(i(c.pop()) * i(c.pop()))));
        add("/", c -> { int r = i(c.pop()); c.push(i(i(c.pop()) / r)); });
        add("%", c -> { int r = i(c.pop()); c.push(i(i(c.pop()) % r)); });
        add("==", c -> c.push(b(c.pop().equals(c.pop()))));
        add("!=", c -> c.push(b(!c.pop().equals(c.pop()))));
        add("<", c -> c.push(b(o(c.pop()).compareTo(o(c.pop())) > 0)));
        add("<=", c -> c.push(b(o(c.pop()).compareTo(o(c.pop())) >= 0)));
        add(">", c -> c.push(b(o(c.pop()).compareTo(o(c.pop())) < 0)));
        add(">=", c -> c.push(b(o(c.pop()).compareTo(o(c.pop())) <= 0)));
        add("&", c -> c.push(b(b(c.pop()) & b(c.pop()))));
        add("|", c -> c.push(b(b(c.pop()) | b(c.pop()))));
        add("!", c -> c.push(b(!b(c.pop()))));
        add("print", c -> c.output(c.pop()));
        add("if", c -> {
            Verb otherwise = c.pop(), then = c.pop();
            if (b(c.pop()))
                c.execute(then);
            else
                c.execute(otherwise);
        });
        add("for", c -> {
            Verb closure = c.pop();
            for (Verb e : (Collection)c.pop()) {
                c.push(e);
                c.execute(closure);
            }
        });
        add("define", c -> c.globals.put((Symbol)c.pop(), c.pop()));
        add("break", Terminator.BREAK);
        add("yield", Terminator.YIELD);
    }
}
