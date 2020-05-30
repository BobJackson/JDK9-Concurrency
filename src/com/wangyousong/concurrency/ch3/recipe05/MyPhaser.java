package com.wangyousong.concurrency.ch3.recipe05;

import java.util.concurrent.Phaser;

/**
 * Implements a subclass of the Phaser class. Overrides the onAdvance method to control
 * the change of phase
 */
public class MyPhaser extends Phaser {

    /**
     * This method is called when the last register thread calls one of the advance methods
     * in the actual phase
     *
     * @param phase             Actual phase
     * @param registeredParties Number of registered threads
     * @return false to advance the phase, true to finish
     */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        return switch (phase) {
            case 0 -> studentsArrived();
            case 1 -> finishFirstExercise();
            case 2 -> finishSecondExercise();
            case 3 -> finishThirdExercise();
            case 4 -> finishExam();
            default -> true;
        };
    }

    /**
     * This method is called in the change from phase 0 to phase 1
     *
     * @return false to continue with the execution
     */
    private boolean studentsArrived() {
        System.out.print("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: We have %d students.\n", getRegisteredParties());
        return false;
    }

    /**
     * This method is called in the change from phase 1 to phase 2
     *
     * @return false to continue with the execution
     */
    private boolean finishFirstExercise() {
        System.out.print("Phaser: All the students has finished the first exercise.\n");
        System.out.print("Phaser: It's turn for the second one.\n");
        return false;
    }

    /**
     * This method is called in the change from phase 2 to phase 3
     *
     * @return false to continue with the execution
     */
    private boolean finishSecondExercise() {
        System.out.print("Phaser: All the students have finished the second exercise.\n");
        System.out.print("Phaser: It's time for the third one.\n");
        return false;
    }

    /**
     * This method is called in the change from phase 3 to phase 4
     *
     * @return false to continue with the execution
     */
    private boolean finishThirdExercise() {
        System.out.print("Phaser: All the students have finished the third exercise.\n");
        System.out.print("Phaser: It's time for exam finish.\n");
        return false;
    }

    /**
     * This method is called in the change from phase 4 to phase 5
     *
     * @return true. There are no more phases
     */
    private boolean finishExam() {
        System.out.print("Phaser: All the students has finished the exam.\n");
        System.out.print("Phaser: Thank you for your time.\n");
        return true;
    }

}
