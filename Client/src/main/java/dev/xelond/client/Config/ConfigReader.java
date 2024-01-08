package dev.xelond.client.Config;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigReader {
    // ! Обработка FileNotFoundE
    // ! Check Catch Blocks (CCB)
    private static final String CONFIG_FILE_PATH = "dev/xelond/client/settings.json";
    // private static final String CONFIG_FILE_PATH = String.valueOf(ConfigReader.class.getResource("dev/xelond/client/settings.json"));
    // Функция чтения строк
    public static String getString(String varString) {
        try {
            //Чтение конфига
            JsonObject config = parseConfigFile();
            return config.get(varString).getAsString();
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            // Обработка ошибок, если возникли проблемы с чтением или разбором JSON
            e.printStackTrace();
            return null;
        }
    }
    // Функция чтения чисел (тип Short)
    public static Short getShort(String varShort) {
        try {
            //Чтение конфига
            JsonObject config = parseConfigFile();
            return config.get(varShort).getAsShort();
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            // Обработка ошибок, если возникли проблемы с чтением или разбором JSON
            e.printStackTrace();
            return null;
        }
    }
    // Чтение Boolean значений
    public static Boolean getBoolean(String varBoolean) {
        try {
            // Чтение конфига
            JsonObject config = parseConfigFile();
            return config.get(varBoolean).getAsBoolean();
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            // Обработка ошибок, если возникли проблемы с чтением или разбором JSON
            e.printStackTrace();
            return null;
        }
    }
    // Фунуция чтения списка строк
    public static List<String> getList(String varString) {
        try {
            // Чтение конфига
            JsonObject config = parseConfigFile();
            JsonArray array = config.getAsJsonArray(varString);
            // Перебор всех значений и запись их в output
            List<String> output = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                String element = array.get(i).getAsString();
                output.add(element);
            }
            return output;
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            // Обработка ошибок, если возникли проблемы с чтением или разбором JSON
            e.printStackTrace();
            return null;
        }
    }
    // Функция чтения строк-объектов из масива
    public static String getObject(String varParent, String varString) {
        try {
            // Чтение конфига
            JsonObject config = parseConfigFile();
            JsonObject parent = config.getAsJsonObject(varParent);
            return parent.get(varString).getAsString();
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            // Обработка ошибок, если возникли проблемы с чтением или разбором JSON
            e.printStackTrace();
            return null;
        }
    }
    // Функция чтения файла конфига
    private static JsonObject parseConfigFile() throws IOException, JsonSyntaxException {
        try (FileReader fileReader = new FileReader(CONFIG_FILE_PATH)) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(fileReader).getAsJsonObject();
        }
    }
}