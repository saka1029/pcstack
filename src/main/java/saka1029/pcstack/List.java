package saka1029.pcstack;

import java.util.Collections;
import java.util.Iterator;

public interface List extends Verb, Collection {

    public static List of(Verb... elements) {
        List list = NIL;
        for (int i = elements.length - 1; i >= 0; --i)
            list = Cons.of(elements[i], list);
        return list;
    }

    public static final List NIL = new List() {

        @Override
        public Iterator<Verb> iterator() {
            return Collections.emptyIterator();
        }
        
        @Override
        public String toString() {
            return "()";
        }
    };

    @Override
    default void execute(Context c) {
        c.rstack.addLast(this);
//        for (Verb e : this)
//            c.execute(e);
    }
    
}
