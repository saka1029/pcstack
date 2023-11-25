package saka1029.pcstack;

/**
 * <table border='1' style='border-collapse: collapse'>
 * <tr><th>Enum値</th><th>Verbとして使用</th><th>Context.execute()の戻り値</th></tr>
 * <tr><td>END</td><td>x</td><td>o</td></tr>
 * <tr><td>BREAK</td><td>o</td><td>x</td></tr>
 * <tr><td>YIELD</td><td>o</td><td>o</td></tr>
 * </table>
 */
public enum Terminator implements Value {
    END,
    BREAK,
    BREAK2,
    YIELD;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
