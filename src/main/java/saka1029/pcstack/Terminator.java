package saka1029.pcstack;

public class Terminator implements Value {
    
    final String name;
    
    Terminator(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public static final Terminator BREAK = new Terminator("break");
    public static final Terminator YIELD = new Terminator("yield");

}
