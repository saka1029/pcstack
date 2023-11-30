package saka1029.pcstack;

public interface List extends Verb, Collection {

    public static List of(Verb... elements) {
        List list = NIL;
        for (int i = elements.length - 1; i >= 0; --i)
            list = Cons.of(elements[i], list);
        return list;
    }
    
    public static List force(Verb v) {
        return v instanceof List list ? list : List.of(v);
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

    @Override
    default void execute(Context c) {
        c.rstack.addLast(this.iterator());
    }
    
}
