package dev.xelond.server.HTTPServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dev.xelond.server.Config.Config;
import dev.xelond.server.Debug;
import dev.xelond.server.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

// ? Я перпеишу этот класс..
public class HTTPServer implements Runnable {
    private static HttpServer server;
    private static final Map<String, String> pathToFileMap = new HashMap<>();

    @Override
    public void run() {
        try {
            server = HttpServer.create(new InetSocketAddress(Config.HostName, Config.HostPort), 0);

            // ! Нужно проверить
            /*
            // ? В настройках необходимо добавить варианты настроек сертификатов
            // ? Генерировать самоподписанный сертификат SSL / Использовать свой SSL / Игнорировать предупреждения

            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // ! Игнорирование предупреждение SSL не рекомендуется
            // Игнорирование SSL предупреждений
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                public X509Certificate[] getAcceptedIssuers() { return null; }
            }}, null);

            server.setHttpsMain.configurator(new HttpsMain.configurator(sslContext));
             */

            // Add server paths and file paths to the map
            addDirectory(System.getProperty("user.home") + "/Documents/ICELauncher/shared/"); // * e.x.
            //HttpFiles.addToShare("clients\\"); // * It is local

            pathToFileMap.forEach((serverPath, filePath) -> server.createContext(serverPath, exchange -> handleRequest(exchange, filePath))); // Creating server context
            server.setExecutor(null); // Use the default executor
            server.start(); // Starting server

            Debug.basic("HTTP server is running using " + Config.HostName + ":" + Config.HostPort);
        } catch (BindException e) {
            Debug.basic("HTTP server " + Config.HostName + ":" + Config.HostPort + "is running already.");
        } catch (IOException e) {
            Debug.basic("Failed to start HTTP server using " + Config.HostName + ":" + Config.HostPort);
            e.printStackTrace();
        }
    }

    private static void handleRequest(HttpExchange exchange, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            send404NotFound(exchange);
            return;
        }

        // Set the response headers
        exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
        exchange.sendResponseHeaders(200, file.length());

        // Write the file content to the response body
        try (OutputStream os = exchange.getResponseBody();
             FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void send404NotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
    }
    public void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
            Debug.basic("HTTP server " + Config.HostName + ":" + Config.HostPort + " is stopped.");
        } else {
            Debug.basic("HTTP server is not running.");
        }
    }
    public static void addDirectory(String baseDirectoryPath) {
        Debug.log("Syncing: \"" + baseDirectoryPath + "\"");

        File baseDirectory = new File(baseDirectoryPath);

        listFiles(baseDirectory, baseDirectory);

        // Debugging
        for (Map.Entry<String, String> entry : pathToFileMap.entrySet()) {
            //System.out.println("Added to share: \"" + entry.getKey() + "\"");
            Debug.log("Sharing Debug: " + entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void listFiles(File baseDirectory, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Рекурсивный вызов для вложенной папки
                    listFiles(baseDirectory, file);
                } else {
                    // Получаем относительный путь от базовой папки
                    String relativePath = baseDirectory.toURI().relativize(file.toURI()).getPath();
                    // Добавляем полный путь к файлу в список
                    pathToFileMap.put("/" + relativePath, file.getAbsolutePath());
                }
            }
        }
    }
}