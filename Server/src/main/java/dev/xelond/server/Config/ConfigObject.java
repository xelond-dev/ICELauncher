package dev.xelond.server.Config;

public class ConfigObject {
    public static Config getDetails() {
        return new Config (
                ConfigReader.getString("HostName"),
                ConfigReader.getShort("HostPort"),
                ConfigReader.getBoolean("ICELauncherAutoUpdates"),
                ConfigReader.getBoolean("ICELauncherPreReleases"),
                ConfigReader.getList("ICELauncherNativesMirrors"),
                ConfigReader.getString("LauncherName"),
                ConfigReader.getBoolean("Launch4J"),
                parseLaunch4JConfig()
        );
    }

    private static Config.Launch4JConfig parseLaunch4JConfig() {
        // Assuming you have a method to read the Launch4JConfig details from the ConfigReader.
        // This is just a placeholder; you need to implement the actual logic.

        return new Config.Launch4JConfig(
                ConfigReader.getObject("Launch4JConfig", "ProductName"),
                ConfigReader.getObject("Launch4JConfig", "FileDescription"),
                ConfigReader.getObject("Launch4JConfig", "InternalName"),
                ConfigReader.getObject("Launch4JConfig", "Copyright"),
                ConfigReader.getObject("Launch4JConfig", "Trademarks")
        );
    }
}
