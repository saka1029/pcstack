package saka1029.pcstack;

public class Range implements List {
    
    final int start, end, step;
    
    Range(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }
    
    public static Range of(int start, int end, int step) {
        return new Range(start, end, step);
    }
    
    public static Range of(int start, int end) {
        return of(start, end, start <= end ? 1 : -1);
    }
    
    public static Range of(int end) {
        return of(1, end, 1);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            int current = start;
            
            @Override
            public Verb next() {
                if (step >= 0 ? current > end : current < end)
                    return null;
                int result = current;
                current += step;
                return Int.of(result);
            }
        };
    }
    
    @Override
    public String toString() {
        return "range(%d, %d, %d)".formatted(start, end, step);
    }
}
