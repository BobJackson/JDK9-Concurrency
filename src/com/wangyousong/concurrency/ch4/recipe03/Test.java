package com.wangyousong.concurrency.ch4.recipe03;

public class Test {

    // to verify java.util.concurrent.ExecutionException: java.lang.Exception: Error validating user
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Main.main(new String[]{"hello", "world"});
        }
    }
}
