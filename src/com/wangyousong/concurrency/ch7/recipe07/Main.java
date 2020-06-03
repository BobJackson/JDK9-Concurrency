package com.wangyousong.concurrency.ch7.recipe07;

/**
 * Main class of the example. It creates an account, a company and a bank
 * to work with the account. The final balance should be equal to the initial, but....
 */
public class Main {

    /**
     * Main method of the example
     * Basically, the operation gets the value of the variable, changes
     * the value to a local variable, and then tries to change the old value with the new one. If the
     * old value is still the same, it is substituted; if not, the method begins the operation again.
     * This operation is called Compare and Set（CAS）.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Creates a new account ...
        Account account = new Account();
        // an initialize its balance to 1000
        account.setBalance(1000);

        // Creates a new Company and a Thread to run its task
        Company company = new Company(account);
        Thread companyThread = new Thread(company);
        // Creates a new Bank and a Thread to run its task
        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);

        // Prints the initial balance
        System.out.printf("Account : Initial Balance: %d\n", account.getBalance());

        // Starts the Threads
        companyThread.start();
        bankThread.start();

        try {
            // Wait for the finalization of the Threads
            companyThread.join();
            bankThread.join();
            // Print the final balance
            System.out.printf("Account : Final Balance: %d\n", account.getBalance());
            System.out.printf("Account : Number of Operations: %d\n", account.getOperations());
            System.out.printf("Account : Accumulated commisions: %f\n", account.getCommission());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
