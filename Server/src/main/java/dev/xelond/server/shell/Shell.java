package dev.xelond.server.shell;

import dev.xelond.server.Config.Config;
import dev.xelond.server.HTTPServer.HTTPServer;
import dev.xelond.server.Main;

import java.util.Scanner;

public class Shell {
    private static Scanner scanner = new Scanner(System.in);

    public static void run() {
        while (true) {
            System.out.print("ICELauncher @ " + Config.LauncherName + " $ ");
            String command = scanner.nextLine();
            handle(command);
        }
    }

    public static void handle(String command) {
        switch (command.toLowerCase()) {
            case "version": {
                System.out.println("> " + Main.ICELauncherVersion);
                break;
            }

            case "httpstart": {
                (new HTTPServer()).run();
                break;
            }

            case "httpstop": {
                (new HTTPServer()).stop();
                break;
            }

            case "httprestart": {
                (new HTTPServer()).stop();
                (new HTTPServer()).run();
                break;
            }

            case "stop": {
                (new HTTPServer()).stop();
                System.exit(0);
                break;
            }
        }
    }
}
