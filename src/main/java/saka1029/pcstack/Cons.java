package saka1029.pcstack;

import java.util.Objects;

public class Cons implements List {
    
    public final Verb car;
    public final List cdr;  // ドットペアは作れない点に注意する。
    
    Cons(Verb car, List cdr) {
        this.car = car;
        this.cdr = cdr;
    }
    
    public static Cons of(Verb car, List cdr) {
        return new Cons(car, cdr);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            List list = Cons.this;
            
            @Override
            public Verb next() {
                if (!(list instanceof Cons c))
                    return null;
                Verb result = c.car;
                list = c.cdr;
                return result;
            }
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, cdr);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Cons p && p.car.equals(car) && p.cdr.equals(cdr);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(car);
        for (List e = cdr; e instanceof Cons c; e = c.cdr)
            sb.append(" ").append(c.car);
        sb.append(")");
        return sb.toString();
    }
}
