package client;

import settings.SettingsFileReader;

public class ClientMain {

    public static void main(String[] args) {
        SettingsFileReader settingsFileReader = new SettingsFileReader();
        int port = settingsFileReader.getPort();
        String host = settingsFileReader.getHost();
        try {
            new Client(host, port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}