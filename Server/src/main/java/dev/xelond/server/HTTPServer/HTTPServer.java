package dev.xelond.server.HTTPServer;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dev.xelond.server.Config.Config;
import dev.xelond.server.Debug;

import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HTTPServer implements Runnable {

    private static HttpServer server;

    @Override
    public void run() {
        try {
            server = HttpServer.create(new InetSocketAddress(Config.HostName, Config.HostPort), 0);
            server.createContext("/", new FileHandler());
            server.setExecutor(null); // Use the default executor
            server.start(); // Starting server

            Debug.basic("HTTP server is running using " + Config.HostName + ":" + Config.HostPort);
        } catch (BindException e) {
            Debug.basic("HTTP server " + Config.HostName + ":" + Config.HostPort + "is running already.");
        } catch (IOException e) {
            Debug.basic("Failed to start HTTP server using " + Config.HostName + ":" + Config.HostPort);
            Debug.printStackTrace(e.toString());
        }
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestMethod = exchange.getRequestMethod();
            String requestUri = exchange.getRequestURI().toString();
            String pathStr = "." + requestUri;
            Path path = Paths.get(pathStr).normalize();
            StringBuilder response = new StringBuilder();

            if (Files.exists(path)) {
                if (Files.isDirectory(path)) {
                    List<Path> files = Files.list(path).toList();
                    response.append("<html><body>");
                    response.append("<h1>Files and Directories:</h1>");
                    response.append("<ul>");
                    for (Path file : files) {
                        String name = file.getFileName().toString();
                        if (Files.isDirectory(file)) {
                            response.append("<li><a href=\"").append(name).append("/\">").append(name).append("/</a></li>");
                        } else {
                            response.append("<li><a href=\"").append(name).append("\">").append(name).append("</a></li>");
                        }
                    }
                    response.append("</ul>");
                    response.append("</body></html>");
                } else {
                    exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=\"" + path.getFileName() + "\"");
                    exchange.sendResponseHeaders(200, Files.size(path));
                    Files.copy(path, exchange.getResponseBody());
                    return;
                }
            } else {
                response.append("Path does not exist.");
            }

            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.toString().getBytes());
            }
        }
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
}
