package com.wangyousong.concurrency.ch7.recipe06;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class HashFiller implements Runnable {

    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>> userHash;

    public HashFiller(ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>> userHash) {
        this.userHash = userHash;
    }

    @Override
    public void run() {

        Random randomGenerator = new Random();
        for (int i = 0; i < 100; i++) {
            Operation operation = new Operation();
            String user = "USER" + randomGenerator.nextInt(100);
            operation.setUser(user);
            String action = "OP" + randomGenerator.nextInt(10);
            operation.setOperation(action);
            operation.setTime(new Date());

            addOperationToHash(userHash, operation);
        }

    }

    private void addOperationToHash(ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>> userHash,
                                    Operation operation) {

        // To solve problem : If two threads want to add the same key at the same time, you can lose the data
        // inserted by one of the threads and have a data-race condition
        ConcurrentLinkedDeque<Operation> opList = userHash.computeIfAbsent(operation.getUser(), user -> new ConcurrentLinkedDeque<>());

        opList.add(operation);

    }

}
