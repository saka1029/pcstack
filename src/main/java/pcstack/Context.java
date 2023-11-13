package pcstack;

public class Context {

    public final Verb[] stack;
    public int sp = 0;
    
    Context(int stackSize) {
        this.stack = new Verb[stackSize];
    }
    
    public static Context of(int stackSize) {
        return new Context(stackSize);
    }
}
