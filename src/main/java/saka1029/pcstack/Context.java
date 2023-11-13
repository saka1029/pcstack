package saka1029.pcstack;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Context {

    final Verb[] stack;
    public int sp = 0;

    final Deque<Iterator<Verb>> rstack = new LinkedList<>();
    final Map<Symbol, Verb> globals = new HashMap<>();
    
    Context(int stackSize) {
        this.stack = new Verb[stackSize];
    }
    
    public static Context of(int stackSize) {
        return new Context(stackSize);
    }
    
    public Verb pop() {
        return stack[--sp];
    }

    public void push(Verb v) {
        stack[sp++] = v;
    }
    
    public void execute(Verb v) {
        v.execute(this);
    }
    
    @Override
    public String toString() {
        return IntStream.range(0, sp)
            .mapToObj(i -> "" + stack[i])
            .collect(Collectors.joining(" ", "[", "]"));
    }
}
