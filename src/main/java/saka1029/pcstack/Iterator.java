package saka1029.pcstack;

public interface Iterator {

    Verb next();
    
    public static Iterator EMPTY = () -> null;
}
