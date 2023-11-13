package saka1029.pcstack;

public class Bool implements Value, Ordered {
    
    public static final Bool TRUE = new Bool(true);
    public static final Bool FALSE = new Bool(false);

    public final boolean value;
    
    private Bool(boolean value) {
        this.value = value;
    }
    
    public static Bool of(boolean value) {
        return value ? TRUE : FALSE;
    }
    
    @Override
    public int compareTo(Ordered o) {
        return Boolean.compare(value, ((Bool)o).value);
    }
    
    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
