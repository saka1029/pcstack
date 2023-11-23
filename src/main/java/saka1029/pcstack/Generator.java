package saka1029.pcstack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Generator implements Value, Collection {
    
    final Context context;
    final List codes;
    
    Generator(Context context, List codes) {
        this.context = context;
        this.codes = codes;
    }

    public static Generator of(Context parent, List codes) {
        return new Generator(parent.child(), codes);
    }

    class Iter implements Iterator<Verb> {
        
        Verb v;
        
        Iter() {
            context.rstack.addLast(codes.iterator());
            v = advance();
        }

        Verb advance() {
            while (true) {
                Terminator t = context.execute();
                switch (t) {
                    case END:
                        return null;
                    case YIELD:
                        return context.pop();
                    default:
                        throw new RuntimeException("Unknown Terminator");
                }
            }
        }

        @Override
        public boolean hasNext() {
            return v != null;
        }

        @Override
        public Verb next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Verb r = v;
            v = advance();
            return r;
        }
        
    }

    @Override
    public Iterator<Verb> iterator() {
        return new Iter();
    }
    
    @Override
    public String toString() {
        return "Generator%s".formatted(codes);
    }
}
