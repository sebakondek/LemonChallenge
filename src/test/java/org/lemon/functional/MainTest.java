package org.lemon.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lemon.configuration.Main;
import org.lemon.configuration.module.Application;

public class MainTest {

    @Test
    public void testMain() {
        Main.main(null);

        Assertions.assertNotNull(Application.getInstance(Main.class));
        Assertions.assertNotNull(Application.getInjector(Application.APP));
    }
}
