package saka1029.pcstack;

public class Quote implements Verb {
    
    public final Verb value;
    
    Quote(Verb value) {
        this.value = value;
    }
    
    public static Quote of(Verb value) {
        return new Quote(value);
    }
    
    @Override
    public void execute(Context c) {
        c.push(value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Quote q && q.value.equals(value);
    }

    @Override
    public String toString() {
        return "'" + value;
    }

}
