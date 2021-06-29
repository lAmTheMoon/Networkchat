package server;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private final String NAME;
    private static AtomicInteger defaultNameID = new AtomicInteger(1);

    public User(String NAME) {
        if (NAME.trim().equals("")) {
            NAME = "User" + defaultNameID.getAndIncrement();
        }
        this.NAME = NAME;
    }

    public String getNAME() {
        return NAME;
    }
}