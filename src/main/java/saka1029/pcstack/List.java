package saka1029.pcstack;

public interface List extends Verb, Collection {

    public static List of(Verb... elements) {
        List list = NIL;
        for (int i = elements.length - 1; i >= 0; --i)
            list = Cons.of(elements[i], list);
        return list;
    }
    
    public static List asList(Verb verb) {
        return verb instanceof List list ? list : Cons.of(verb, List.NIL);
    }

    @Override
    default void execute(Context c) {
        c.rpush(iterator());
    }
    
    public static final List NIL = new List() {

        @Override
        public Iterator iterator() {
            return Iterator.EMPTY;
        }
        
        @Override
        public String toString() {
            return "()";
        }
    };
}
