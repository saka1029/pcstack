package test.saka1029.pcstack;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import saka1029.Common;

public class TestDeque {

    static Logger logger = Common.logger(TestDeque.class);

    @Test
    public void testLinkedList() {
        Deque<Integer> stack = new LinkedList<>();
        stack.addLast(1);
        stack.addLast(2);
        stack.addLast(3);
        Deque<Integer> expected = new LinkedList<>();
        expected.addLast(1);
        expected.addLast(2);
        expected.addLast(3);
        assertEquals(expected, stack);
    }

    @Test
    public void testMixed() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.addLast(1);
        stack.addLast(2);
        stack.addLast(3);
        Deque<Integer> expected = new LinkedList<>();
        expected.addLast(1);
        expected.addLast(2);
        expected.addLast(3);
        assertEquals(expected, new LinkedList<>(stack));
    }

    @Test
    public void testArrayDeque() {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.addLast(1);
        stack.addLast(2);
        stack.addLast(3);
        assertEquals(List.of(1, 2, 3), new LinkedList<>(stack));
        assertEquals(3, (int)stack.removeLast());
        assertEquals(List.of(1, 2), new LinkedList<>(stack));
    }
}
