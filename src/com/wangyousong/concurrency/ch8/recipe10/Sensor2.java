package com.wangyousong.concurrency.ch8.recipe10;

/**
 * Class that simulates a sensor in the doors of the parking
 */
public class Sensor2 implements Runnable {

    /**
     * Counter of cars in the parking
     */
    private final ParkingCounter counter;

    /**
     * Constructor of the class. It initializes its attributes
     *
     * @param counter Counter of cars in the parking
     */
    public Sensor2(ParkingCounter counter) {
        this.counter = counter;
    }

    /**
     * Main method of the sensor. Simulates the traffic in the door of the parking
     */
    @Override
    public void run() {
        counter.carIn();
        counter.carOut();
        counter.carOut();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
    }

}
