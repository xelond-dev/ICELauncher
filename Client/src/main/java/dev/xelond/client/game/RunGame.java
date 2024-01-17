package dev.xelond.client.game;

import dev.xelond.client.Config.Config;
import dev.xelond.client.Config.ConfigObject;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//class SyncPipe implements Runnable {
//    public SyncPipe(InputStream istrm, OutputStream ostrm) {
//        istrm_ = istrm;
//        ostrm_ = ostrm;
//    }
//    public void run() {
//        try
//        {
//            final byte[] buffer = new byte[1024];
//            for (int length = 0; (length = istrm_.read(buffer)) != -1; )
//            {
//                ostrm_.write(buffer, 0, length);
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    private final OutputStream ostrm_;
//    private final InputStream istrm_;
//}

public class RunGame {

    static final Config conf = ConfigObject.getDetails();
    static final String minecraftClass = "net.minecraft.client.main.Main";
    static final String gameVersion = Config.Version;
    static String LauncherNameDirectory = "/." + Config.LauncherName.toLowerCase().replaceAll("[^\\w]", "");
    static final File clientDir = new File(System.getProperty("user.home") + LauncherNameDirectory);
    static final File versionDir = new File(clientDir.getAbsolutePath() + "/versions/" + gameVersion);
    static final File nativesDir = new File(versionDir.getAbsolutePath() + "/natives");
    static final File librariesDir = new File(clientDir.getAbsolutePath() + "/libraries");


    public static void launch(String username) throws IOException, InterruptedException {

//        String[] command =
//                {
//                        "\\AppData\\Roaming\\.test_launcher\\runtime\\java-runtime-gamma\\windows-x64\\java-runtime-gamma\\bin\\java.exe",
//                        "-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump",
//                        "-Djava.library.path=\\AppData\\Roaming\\.test_launcher\\versions\\1.20.4\\natives",
//                        "-Djna.tmpdir=\\AppData\\Roaming\\.test_launcher\\versions\\1.20.4\\natives",
//                        "-Dorg.lwjgl.system.SharedLibraryExtractPath=\\AppData\\Roaming\\.test_launcher\\versions\\1.20.4\\natives",
//                        "-Dio.netty.native.workdir=\\AppData\\Roaming\\.test_launcher\\versions\\1.20.4\\natives",
//                        "-Dminecraft.launcher.brand=minecraft-launcher-lib",
//                        "-Dminecraft.launcher.version=6.4",
//                        "-cp",
//                        "\\AppData\\Roaming\\.test_launcher\\libraries\\com\\github\\oshi\\oshi-core\\6.4.5\\oshi-core-6.4.5.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\google\\code\\gson\\gson\\2.10.1\\gson-2.10.1.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\google\\guava\\failureaccess\\1.0.1\\failureaccess-1.0.1.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\google\\guava\\guava\\32.1.2-jre\\guava-32.1.2-jre.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\ibm\\icu\\icu4j\\73.2\\icu4j-73.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\authlib\\6.0.52\\authlib-6.0.52.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\blocklist\\1.0.10\\blocklist-1.0.10.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\brigadier\\1.2.9\\brigadier-1.2.9.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\datafixerupper\\6.0.8\\datafixerupper-6.0.8.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\logging\\1.1.1\\logging-1.1.1.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\patchy\\2.2.10\\patchy-2.2.10.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\com\\mojang\\text2speech\\1.17.9\\text2speech-1.17.9.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\commons-codec\\commons-codec\\1.16.0\\commons-codec-1.16.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\commons-io\\commons-io\\2.13.0\\commons-io-2.13.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\commons-logging\\commons-logging\\1.2\\commons-logging-1.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-buffer\\4.1.97.Final\\netty-buffer-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-codec\\4.1.97.Final\\netty-codec-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-common\\4.1.97.Final\\netty-common-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-handler\\4.1.97.Final\\netty-handler-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-resolver\\4.1.97.Final\\netty-resolver-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-transport-classes-epoll\\4.1.97.Final\\netty-transport-classes-epoll-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-transport-native-unix-common\\4.1.97.Final\\netty-transport-native-unix-common-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\io\\netty\\netty-transport\\4.1.97.Final\\netty-transport-4.1.97.Final.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\it\\unimi\\dsi\\fastutil\\8.5.12\\fastutil-8.5.12.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\net\\java\\dev\\jna\\jna-platform\\5.13.0\\jna-platform-5.13.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\net\\java\\dev\\jna\\jna\\5.13.0\\jna-5.13.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\net\\sf\\jopt-simple\\jopt-simple\\5.0.4\\jopt-simple-5.0.4.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\commons\\commons-compress\\1.22\\commons-compress-1.22.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\commons\\commons-lang3\\3.13.0\\commons-lang3-3.13.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\httpcomponents\\httpclient\\4.5.13\\httpclient-4.5.13.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\httpcomponents\\httpcore\\4.4.16\\httpcore-4.4.16.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\logging\\log4j\\log4j-api\\2.19.0\\log4j-api-2.19.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\logging\\log4j\\log4j-core\\2.19.0\\log4j-core-2.19.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\apache\\logging\\log4j\\log4j-slf4j2-impl\\2.19.0\\log4j-slf4j2-impl-2.19.0.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\joml\\joml\\1.10.5\\joml-1.10.5.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.3.2\\lwjgl-glfw-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.3.2\\lwjgl-glfw-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.3.2\\lwjgl-glfw-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-glfw\\3.3.2\\lwjgl-glfw-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.3.2\\lwjgl-jemalloc-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.3.2\\lwjgl-jemalloc-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.3.2\\lwjgl-jemalloc-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-jemalloc\\3.3.2\\lwjgl-jemalloc-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-openal\\3.3.2\\lwjgl-openal-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-openal\\3.3.2\\lwjgl-openal-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-openal\\3.3.2\\lwjgl-openal-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-openal\\3.3.2\\lwjgl-openal-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.3.2\\lwjgl-opengl-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.3.2\\lwjgl-opengl-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.3.2\\lwjgl-opengl-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-opengl\\3.3.2\\lwjgl-opengl-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-stb\\3.3.2\\lwjgl-stb-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-stb\\3.3.2\\lwjgl-stb-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-stb\\3.3.2\\lwjgl-stb-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-stb\\3.3.2\\lwjgl-stb-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.3.2\\lwjgl-tinyfd-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.3.2\\lwjgl-tinyfd-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.3.2\\lwjgl-tinyfd-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl-tinyfd\\3.3.2\\lwjgl-tinyfd-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl\\3.3.2\\lwjgl-3.3.2.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl\\3.3.2\\lwjgl-3.3.2-natives-windows.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl\\3.3.2\\lwjgl-3.3.2-natives-windows-arm64.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\lwjgl\\lwjgl\\3.3.2\\lwjgl-3.3.2-natives-windows-x86.jar;\\AppData\\Roaming\\.test_launcher\\libraries\\org\\slf4j\\slf4j-api\\2.0.7\\slf4j-api-2.0.7.jar;\\AppData\\Roaming\\.test_launcher\\versions\\1.20.4\\1.20.4.jar",
//                        "net.minecraft.client.main.Main",
//                        "--username",
//                        username,
//                        "--version",
//                        "1.20.4",
//                        "--gameDir",
//                        "\\AppData\\Roaming\\.test_launcher",
//                        "--assetsDir",
//                        "\\AppData\\Roaming\\.test_launcher\\assets",
//                        "--assetIndex",
//                        "12",
//                        //"--uuid",
//                        //"",
//                        "--accessToken",
//                        "",
//                        "--clientId",
//                        "${clientid}",
//                        "--xuid",
//                        "${auth_xuid}",
//                        "--userType",
//                        "msa",
//                        "--versionType",
//                        "release"
//                };
//        Process p = Runtime.getRuntime().exec(command);
//        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
//        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
//        PrintWriter stdin = new PrintWriter(p.getOutputStream());
//        // stdin.println("dir c:\\ /A /Q");
//        // write any other commands you want here
//        stdin.close();
//        int returnCode = p.waitFor();
//        System.out.println("Return code = " + returnCode);

        System.out.println("Client directory: " + clientDir.getAbsolutePath());

        if (!nativesDir.exists()) {
            if (!nativesDir.mkdirs()) {
                System.out.println("Failed to create natives directory.");
                return;
            }
        }

        List<URL> urls = new ArrayList<>();

        try {
//            urls.addAll(getLibs(new File(clientDir.getAbsolutePath() + File.separator + "versions")));
//            urls.addAll(getLibs(new File(clientDir.getAbsolutePath() + File.separator + "libraries")));
           urls.addAll(getLibs(versionDir));
           urls.addAll(getLibs(librariesDir));
        } catch (Exception e) {
            System.out.println("Invalid client path.");
            return;
        }

        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]));

        System.out.println("Loaded JARs: " + urls);
        System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
        System.setProperty("fml.ignorePatchDiscrepancies", "true");
        System.setProperty("org.lwjgl.librarypath", nativesDir.getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", nativesDir.getAbsolutePath());
        System.setProperty("java.library.path", nativesDir.getAbsolutePath());
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

        List<String> params = new ArrayList<>();

        // ! Разобрать
        try {
            classLoader.loadClass("com.mojang.authlib.Agent");
            params.add("--accessToken");
            params.add("");
            //params.add("--uuid");
            //params.add("12345");
            params.add("--userProperties"); //
            params.add("{}");
            params.add("--assetIndex");
            params.add(gameVersion);
        } catch (ClassNotFoundException e) {
            params.add("--session");
            params.add("1");
        }

        params.add("--username");
        params.add(username);
        params.add("--version");
        params.add(gameVersion);
        params.add("--gameDir");
        params.add(clientDir.getAbsolutePath() + "/");
        params.add("--assetsDir");
        params.add(clientDir.getAbsolutePath() + "/assets");


        try {
            Class < ? > mainClass = classLoader.loadClass(minecraftClass);
            System.out.println("Launching the client");
            Method mainMethod = mainClass.getMethod("main", String[].class);
            mainMethod.invoke(null, new Object[] { params.toArray(new String[0]) });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static List<URL> getLibs(File libsFolder) throws MalformedURLException {
        List<URL> libs = new ArrayList<>();
        if (!libsFolder.exists() && !libsFolder.mkdirs()) {
            System.out.println("Failed to create libraries directory.");
            return libs;
        }

        for (File file : Objects.requireNonNull(libsFolder.listFiles())) {
            if (file.isDirectory()) {
                libs.addAll(getLibs(file));
            } else if (file.getName().endsWith(".jar")) {
                libs.add(file.toURI().toURL());
            }
        }
        return libs;
    }
}