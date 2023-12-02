package saka1029.pcstack;

public class Generator implements List {

    final Context context;
    final List code;
    
    Generator(Context context, Verb code) {
        this.context = context;
        this.code = List.asList(code);
    }
    
    public static Generator of(Context origin, Verb list) {
        return new Generator(origin.child(), list);
    }
    
    class Iter implements Iterator {
        
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
        public Verb next() {
            Verb result = current;
            if (result != null)
                current = advance();
            return result;
        }
    }

    @Override
    public Iterator iterator() {
        return new Iter();
    }
    
    @Override
    public String toString() {
        return "generator[%s]".formatted(code);
    }

}
