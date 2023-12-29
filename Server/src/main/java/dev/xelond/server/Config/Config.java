package dev.xelond.server.Config;

import java.util.ArrayList;
import java.util.List;

public final class Config {
    public static String HostName;
    public static short HostPort;
    public static boolean ICELauncherAutoUpdates;
    public static boolean ICELauncherPreReleases;
    public List<String> ICELauncherNativesMirrors = new ArrayList<>();
    public static String LauncherName;
    public static boolean Launch4J;
    public Launch4JConfig launch4JConfig;

    public Config(
            String HostName, short HostPort,
            boolean ICELauncherAutoUpdates, boolean ICELauncherPreReleases,
            List<String> ICELauncherNativesMirrors, String LauncherName,
            boolean Launch4J, Launch4JConfig Launch4JConfig) {
        
        this.HostName = HostName;
        this.HostPort = HostPort;
        this.ICELauncherAutoUpdates = ICELauncherAutoUpdates;
        this.ICELauncherPreReleases = ICELauncherPreReleases;
        this.ICELauncherNativesMirrors = ICELauncherNativesMirrors;
        this.LauncherName = LauncherName;
        this.Launch4J = Launch4J;
        this.launch4JConfig = Launch4JConfig;
    }

    public static class Launch4JConfig {
        public String ProductName;
        public String FileDescription;
        public String InternalName;
        public String Copyright;
        public String Trademarks;

        public Launch4JConfig(
                String ProductName, String FileDescription,
                String InternalName, String Copyright,
                String Trademarks) {

            this.ProductName = ProductName;
            this.FileDescription = FileDescription;
            this.InternalName = InternalName;
            this.Copyright = Copyright;
            this.Trademarks = Trademarks;
        }
    }

}
