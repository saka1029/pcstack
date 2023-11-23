package saka1029.pcstack;

public enum Terminator implements Value {
    END,
    BREAK,
    YIELD;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
