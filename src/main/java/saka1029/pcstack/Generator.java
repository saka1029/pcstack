package saka1029.pcstack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Generator implements Value, Collection {

    final Context context;
    final List code;
    
    Generator(Context context, List code) {
        this.context = context;
        this.code = code;
    }
    
    public static Generator of(Context origin, Verb verb) {
        return new Generator(origin.child(), verb instanceof List list ? list : List.of(verb));
    }
    
    class Iter implements Iterator<Verb> {
        
        Verb current;
        
        Iter() {
            context.execute(code);
            this.current = advance();
        }
        
        Verb advance() {
            Terminator t = context.execute();
            switch (t) {
                case END:
                    return null;
                case YIELD:
                    return context.pop();
                default:
                    throw new RuntimeException("Unsupported terminator '%s'".formatted(t));
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Verb next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Verb result = current;
            current = advance();
            return result;
        }
        
    }

    @Override
    public Iterator<Verb> iterator() {
        return new Iter();
    }
    
    @Override
    public String toString() {
        return "Generator[%s]".formatted(code);
    }

}
