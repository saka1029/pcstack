package saka1029.pcstack;

import java.util.Iterator;

public class Coroutine implements Verb, Iterable<Verb> {

    final Iterable<Verb> codes;
    
    Coroutine(Iterable<Verb> codes) {
        this.codes = codes;
    }

    @Override
    public void execute(Context c) {
        
    }

    @Override
    public Iterator<Verb> iterator() {
        return new Iterator<Verb>() {
            
            @Override
            public Verb next() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean hasNext() {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }
}
