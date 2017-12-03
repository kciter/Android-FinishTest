package kciter.famuosfood.util;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class BusProvider {
    private static Bus bus = null;

    private BusProvider() {}

    public static Bus getGlobal() {
        if (bus == null) {
            bus = new Bus(ThreadEnforcer.MAIN);
        }

        return bus;
    }
}
