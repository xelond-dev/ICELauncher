package dev.xelond.server;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Debug {
    public static void basic(String basicMessage) {
        String logPrefix = Main.debugMode ? "[B] " : "";
        System.out.println(logPrefix + "[" + getCurrentTime() + "] " + basicMessage); // Basic message.
    }
    public static void log(String logMessage) {
        if (Main.debugMode) {
            System.out.println("[L] [" + getCurrentTime() + "] " + logMessage); // Log message for debug mode.
        }
    }

    public static void error(String errorMessage) {
        if (Main.debugMode) {
            System.err.println("[E] [" + getCurrentTime() + "] " + errorMessage); // Error message for debug mode.
        }
    }
    public static void printStackTrace(String exception) {
        if (Main.debugMode) {
            System.err.println("[PrintStackTrace] [" + getCurrentTime() + "] " + exception); // Error message for debug mode.
        }
    }

    private static String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}

