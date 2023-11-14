package saka1029.pcstack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Generator implements Value, Collection {
    
    public static final Value YIELD = new Value() {
        @Override
        public String toString() {
            return "yield";
        }
    };

    final Context context;
    final Collection codes;
    
    Generator(Context context, Collection codes) {
        this.context = context;
        this.codes = codes;
    }

    public static Generator of(Context context, Collection codes) {
        return new Generator(context, codes);
    }
    
    class Iter implements Iterator<Verb> {
        
        Iterator<Verb> codes = Generator.this.codes.iterator();
        Verb v = advance();
        
        Verb advance() {
            while (codes.hasNext()) {
                context.execute(codes.next());
                if (context.sp >= 2 && context.peek(0) == YIELD) {
                    context.drop(); // drop YIELD;
                    return context.pop();
                }
            }
            return null;
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
}
