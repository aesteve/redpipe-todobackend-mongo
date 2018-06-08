package net.redpipe.examples.utils;


import io.vertx.reactivex.core.Vertx;

public class RxUtils {

    /**
     * Transforms a rx Java 1 Vertx instance into a rx Java 2 Vertx instance
     */
    public static Vertx rx1ToRx2(io.vertx.rxjava.core.Vertx rxjava1Vertx) {
        return  Vertx.newInstance(rxjava1Vertx.getDelegate());
    }

}
