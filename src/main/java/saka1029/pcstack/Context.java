package saka1029.pcstack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

import saka1029.Common;

public class Context {

    static final Logger logger = Common.logger(Context.class);

    final java.util.List<Verb> stack;

    final java.util.List<Iterator> rstack;
    final Map<Symbol, Verb> globals;
    final Consumer<String> output;

    Context(java.util.List<Verb> stack, java.util.List<Iterator> rstack, Map<Symbol, Verb> globals, Consumer<String> output) {
        this.stack = stack;
        this.rstack = rstack;
        this.globals = globals;
        this.output = output;
    }

    Context() {
        this(new ArrayList<>(), new ArrayList<>(), new HashMap<>(), System.out::println);
        init();
    }
    
    public static Context of() {
        return new Context();
    }
    
    public Context child() {
        return new Context(new ArrayList<>(), new ArrayList<>(), globals, output);
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
    
    public int rsp() {
        return rstack.size();
    }

    public void rpush(Iterator iterator) {
        rstack.addLast(iterator);
    }
    
    public Iterator rpop() {
        return rstack.removeLast();
    }
    
    public Iterator rpeek() {
        return rstack.getLast();
    }
    
    public Iterator forEach(Verb closure, List list) {
        return new Iterator() {
            final Iterator iterator = list.iterator();
            final List listClosure = List.asList(closure);
            
            @Override
            public Verb next() {
                Verb element = iterator.next();
                return element == null ? null : Cons.of(element, listClosure);
            }
        };
    }

    public void output(Object v) {
        if (output != null)
            output.accept(v.toString());
    }
    
    public void execute(Verb v) {
        v.execute(this);
//        logger.info(v + " -> " + this);
    }
    
    public Terminator execute() {
        L0: while (rsp() > 0) {
            Iterator iterator = rpeek();
            Verb verb;
            L1: while ((verb = iterator.next()) != null) {
//                logger.info("execute: %s %s".formatted(verb, this));
                execute(verb);
                if (!stack.isEmpty() && stack.getLast() instanceof Terminator terminator) {
                    drop(); // drop Terminator;
                    switch (terminator) {
                        case END:
                            throw new RuntimeException("Terminator 'end' found on stack");
                        case BREAK:
                            break L1;
                        case YIELD:
                            return Terminator.YIELD;
                        default:
                            throw new RuntimeException("Unknown terminator '%s'".formatted(terminator));
                    }
                }
                if (rsp() == 0 || rpeek() != iterator)
                    continue L0;
            }
            rpop();
        }
        return Terminator.END;
    }

//    public Verb eval(Verb v) {
//        int p = sp();
//        execute(v);
//        assert sp() == p + 1 : "sp current=%d previous=%d".formatted(sp(), p);
//        return pop();
//    }
    
    public void run(String s) {
        Reader reader = Reader.of(s);
        Verb e;
        while ((e = reader.read()) != null) {
            execute(e);
            execute();
        }
    }
    
    public Verb eval(String s) {
        int p = sp();
        String pstack = toString();
        run(s);
        assert sp() == p + 1 : "stack current=%s previous=%s".formatted(toString(), pstack);
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
        add("@0", c -> c.dup(0));
        add("@1", c -> c.dup(1));
        add("drop", Context::drop);
        add("swap", Context::swap);
        add("+", c -> c.push(i(i(c.pop()) + i(c.pop()))));
        add("-", c -> c.push(i(-i(c.pop()) + i(c.pop()))));
        add("*", c -> c.push(i(i(c.pop()) * i(c.pop()))));
        add("/", c -> { int r = i(c.pop()); c.push(i(i(c.pop()) / r)); });
        add("%", c -> { int r = i(c.pop()); c.push(i(i(c.pop()) % r)); });
        add("true", Bool.TRUE);
        add("false", Bool.FALSE);
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
        add("stack", c -> c.output(this));
        add("cons", c -> {
            List cdr = (List)c.pop();
            Verb car = c.pop();
            c.push(Cons.of(car, cdr));
        });
        add("uncons", c -> {
            Cons cons = (Cons)c.pop();
            c.push(cons.car);
            c.push(cons.cdr);
        });
        add("if", c -> {
            List otherwise = List.asList(c.pop()), then = List.asList(c.pop());
            boolean cond = b(c.pop());
            if (cond)
                c.execute(then);
            else
                c.execute(otherwise);
        });
        add("for", c -> {
            rpush(forEach(c.pop(), (List)c.pop()));
        });
        add("define", c -> c.globals.put((Symbol)c.pop(), c.pop()));
        add("break", Terminator.BREAK);
        add("yield", Terminator.YIELD);
        add("range", c -> {
            int end = i(c.pop()), start = i(c.pop());
            c.push(Range.of(start, end));
        });
        add("range-step", c -> {
            int step = i(c.pop()), end = i(c.pop()), start = i(c.pop());
            c.push(Range.of(start, end, step));
        });
        add("iota", c -> c.push(Range.of(i(c.pop()))));
        add("generator", c -> c.push(Generator.of(c, (List)c.pop())));
    }
}
