package com.wangyousong.concurrency.ch11.recipe07;

public class DBConnection {

    private static DBConnection connection;

    private DBConnection() {

    }

    public static DBConnection getConnection() {
        if (connection == null) {
            connection = new DBConnection();
        }
        return connection;
    }
}
