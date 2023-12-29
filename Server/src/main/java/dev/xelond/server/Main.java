package dev.xelond.server;

import dev.xelond.server.Config.Config;
import dev.xelond.server.Config.ConfigObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String ICELauncherVersion = "0.0.0-alpha+dev"; // MAJOR.MINOR.PATCH[-PRE_RELEASE][+METADATA]
    public static boolean debugMode = true; // DEVELOPER SWITCH IS HERE (debugMode)
    public static Config config;
    public static void main(String[] args) {

        // Args handler
        for (String arg : args) {
            if (arg.equals("--debug-mode")) {
                debugMode = true;
                Debug.log("Debug mode is enabled!");
                break;
            }
        }


        // Unpacking resource
        String[] filePaths = { "settings.json", "profiles/ExampleServer.json" };

        // Create ExecutorService with a fixed thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(filePaths.length);

        // Unpack resources in parallel
        for (String filePath : filePaths) {
            // Submit
            executorService.submit(() -> {
                Path fileLocalPath = Paths.get("./" + filePath);
                Path directoryPath = fileLocalPath.getParent();

                try {
                    if (directoryPath != null) {
                        Files.createDirectories(directoryPath);
                    }

                    if (Files.exists(fileLocalPath)) {
                        return;  // Skip if file already exists
                    }

                    Debug.log("Unpacking \"" + filePath + "\"");
                    try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath)) {
                        if (inputStream != null) {
                            Files.copy(inputStream, fileLocalPath);
                        } else {
                            Debug.error("Failed unpacking \"" + filePath + "\", inputStream is null");
                        }
                    } catch (IOException e) {
                        Debug.printStackTrace("Error unpacking \"" + filePath + "\": " + e.toString());
                    }
                } catch (IOException e) {
                    Debug.printStackTrace("Error creating directories for \"" + filePath + "\": " + e.toString());
                }
            });
        }

        // Close ExecutorService after all threads have finished working
        executorService.shutdown();

        try {
            // Wait for all threads to finish their work (helps to wait until they finish)
            // But idk, executor service was down already, it is make sense here?

            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // Handling an exception if the waiting thread was interrupted

            Debug.error("Parallel file extraction was interrupted: " + e.getMessage());
        }


        // Read config
        config = ConfigObject.getDetails();
        System.out.println(config.HostName);


        // If u run from IntellijI, use this
        for (String forceDeleteFile : filePaths) {
            try {
                Files.delete(Path.of(forceDeleteFile));
            } catch (IOException e) {
                Debug.printStackTrace(e.toString());
            }
        }

    }
}