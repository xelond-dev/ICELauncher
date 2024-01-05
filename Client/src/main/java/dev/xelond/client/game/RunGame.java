package dev.xelond.client.game;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RunGame {
    public static final String nickname = "Xelond";
    public static final String minecraftClass = "net.minecraft.client.main.Main";
    public static final String gameVersion = "1.20.4";
    //public static final File clientDir = new File("/home/xelond/Documents/ICELauncher/tests/");
    public static final File clientDir = new File(("C:\\Users\\nikit\\Desktop\\LegacyLauncher")); //versions/1.20.4
    // public static final File clientDir = new File(("C:\\Users\\nikit\\AppData\\Roaming\\.minecraft"));
    public static final File versionsDir = new File(clientDir.getAbsolutePath() + "/versions");
    public static final File nativesDir = new File(versionsDir.getAbsolutePath() + "/" + gameVersion + "/natives");
    public static final File librariesDir = new File(clientDir.getAbsolutePath() + "/libraries");


    public static void main(String[] args) {


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
           urls.addAll(getLibs(versionsDir));
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
            params.add("1");
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
        params.add(nickname);
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