package dev.xelond.client.Config;

// import java.util.List;

import java.util.ArrayList;
import java.util.List;

public final class Config {
    // public static String HostName;
    // public static short HostPort;
    public static String LauncherName;
    public static String Version;
    public static String ProfileName;
    public static String ClientFolder;
    public static Boolean AutoJoinToServer;
    public static List<String> ServerProperties;

    public Config (
            // String HostName, short HostPort,
            String LauncherName, String Version,
            String ProfileName, String ClientFolder,
            Boolean AutoJoinToServer, List<String> ServerProperties) {

            // Config.HostName = HostName;
            // Config.HostPort = HostPort;

        Config.LauncherName = LauncherName;
        Config.Version = Version;
        Config.ProfileName = ProfileName;
        Config.ClientFolder = ClientFolder;
        Config.AutoJoinToServer = AutoJoinToServer;
        Config.ServerProperties = ServerProperties;
    }


}
