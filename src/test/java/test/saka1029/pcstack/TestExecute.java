package test.saka1029.pcstack;

import static org.junit.Assert.fail;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.Test;

import saka1029.Common;
import saka1029.pcstack.Int;
import saka1029.pcstack.List;
import saka1029.pcstack.Verb;

public class TestExecute {
    
    static Logger logger = Common.logger(TestExecute.class);

    class C {
        Deque<Iterator<Verb>> stack = new ArrayDeque<>();
        
        void executeList(List list) {
            Iterator<Verb> i = stack.getLast();
            while (i.hasNext())
                execute(i.next());
        }

        void execute(Verb v) {
            if (v instanceof List list) {
                stack.addLast(list.iterator());
                executeList(list);
                stack.removeLast();
            } else
                logger.info(v + "");
        }
        
        void execList(List list) {
            Iterator<Verb> i = list.iterator();
            while (i.hasNext())
                execute(i.next());
        }
        
        void exec(Verb v) {
            if (v instanceof List list)
                for (Verb e : list)
                    exec(e);
            else
                logger.info(v + "");
        }
    }

    static Int i(int i) {
        return Int.of(i);
    }

    @Test
    public void testExecute() {
        C c = new C();
        c.execute(List.of(i(1), List.of(i(11), List.of(i(12))), i(2), List.of(i(22)), i(3)));
    }

    @Test
    public void testExec() {
        C c = new C();
        c.exec(List.of(i(1), List.of(i(11), List.of(i(12))), i(2), List.of(i(22)), i(3)));
    }

}
