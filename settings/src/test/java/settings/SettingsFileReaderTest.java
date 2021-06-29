package settings;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SettingsFileReaderTest {

    @Test
    void getPortFromSettings() {
        String[] result;
        try (BufferedReader reader = new BufferedReader(new FileReader("../settings.txt"))) {
            result = reader.readLine().split(":");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int actual = Integer.parseInt(result[1]);
        assertEquals(23444, actual);
    }

    @Test
    void getHostFromSettings() {

        String[] result;
        try (BufferedReader reader = new BufferedReader(new FileReader("../settings.txt"))) {
            result = reader.readLine().split(":");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = result[0];
        assertEquals("127.0.0.1", actual);
    }
}