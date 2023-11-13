package pcstack;

public interface Value extends Verb {
    @Override
    default void execute(Context c) {
        c.push(this);
    }
}
