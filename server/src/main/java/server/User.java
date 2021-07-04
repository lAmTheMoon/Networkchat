package server;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private final String NAME;
    private static AtomicInteger defaultNameID = new AtomicInteger(1);

    public User(String name) {
        if (name.trim().equals("")) {
            name = "User" + defaultNameID.getAndIncrement();
        }
        this.NAME = name;
    }

    public String getNAME() {
        return NAME;
    }
}