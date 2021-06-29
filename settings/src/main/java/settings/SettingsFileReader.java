package settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SettingsFileReader {

    private final String[] SETTINGS;
    private final File SETTINGS_FILE = new File("settings.txt");

    public SettingsFileReader() {
        this.SETTINGS = readSettingsFile();
    }

    private String[] readSettingsFile() {
        String result = "";
        try (var reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.split(":");
    }

    public int getPort() {
        return Integer.parseInt(SETTINGS[1]);
    }

    public String getHost() {
        return SETTINGS[0];
    }
}