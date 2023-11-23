package test.saka1029.pcstack;

import java.util.logging.Logger;

import org.junit.Test;

import saka1029.Common;
import saka1029.pcstack.Terminator;

public class TestTerminator {
    
    static final Logger logger = Common.logger(TestTerminator.class);

    @Test
    public void test() {
        logger.info(Terminator.YIELD.toString());
    }

}
