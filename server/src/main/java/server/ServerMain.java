package server;

import settings.SettingsFileReader;

public class ServerMain {

    private static final int MAX_NUMBER_OF_CLIENT = 30;

    public static void main(String[] args) {
        SettingsFileReader settingsFileReader = new SettingsFileReader();
        int port = settingsFileReader.getPort();
        new Server(port, MAX_NUMBER_OF_CLIENT).start();
    }
}