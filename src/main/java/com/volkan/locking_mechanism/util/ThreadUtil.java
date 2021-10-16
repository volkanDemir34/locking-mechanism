package com.volkan.locking_mechanism.util;

public final class ThreadUtil {

    private ThreadUtil(){

    }

    public static void sleep(long milisecond){
        try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
