package dev.xelond.client.Config;

public class ConfigObject {
    public static Config getDetails() {
        return new Config (
                // ConfigReader.getString("HostName"),
                // ConfigReader.getShort("HostPort"),
                ConfigReader.getString("LauncherName"),
                ConfigReader.getString("Version"),
                ConfigReader.getString("ProfileName"),
                ConfigReader.getString("ClientFolder"),
                ConfigReader.getBoolean("AutoJoinToServer"),
                ConfigReader.getList("ServerProperties")
        );
    }

}
