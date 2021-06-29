package server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

class UserTest {

    private static AtomicInteger defaultNameID = new AtomicInteger(1);

    @Test
    void ensureUserGetDefaultNameWhenNameIsNull() {
        String expected = "User" + defaultNameID.getAndIncrement();
        User user = new User("");
        assertEquals(user.getNAME(), expected);
    }

    @Test
    void ensureUserGetDefaultNameWhenNameIsSpace() {
        String expected = "User" + defaultNameID.getAndIncrement();
        User user2 = new User("        ");
        assertEquals(user2.getNAME(), expected);
    }
}