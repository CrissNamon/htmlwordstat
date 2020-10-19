package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.IInstance;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application implements IInstance {

    /**
     * Экземпляр Singleton
     */
    private static Application Instance;
    /**
     * Путь к файлу лога
     */
    private String logPath;

    private Application() {
        this.logPath = getExecuteDirectory()
                +"log.txt";
    }

    /**
     * @return экземпляр Application
     */
    public static Application getInstance() {
        if (Instance == null) {
            Instance = new Application();
        }
        return Instance;
    }

    /**
     * Устанавливает путь к файлу лога
     * @param logPath путь к файлу
     */
    public void setLogPath(String logPath) {
        if (!logPath.endsWith("/")) {
            logPath += "/";
        }
        this.logPath = logPath+"log.txt";
    }

    /**
     * Возвращает установленный путь к файлу лога
     * @return путь к файлу
     */
    public String getLogPath() {
        return logPath;
    }

    /**
     * Возвращает словарь аргументов и их значений
     * @param args Аргументы командной строки
     */
    public Map<String, List<String>> getCLIParams(String[] args) {
        final Map<String, List<String>> params = new HashMap<>();
        List<String> options = null;
        for (final String a : args) {
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    continue;
                }
                options = new ArrayList<>();
                params.put(a.substring(1), options);
            } else if (options != null) {
                options.add(a);
            }
        }
        return params;
    }

    /**
     * Добавляет ошибку в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public static boolean logError(String text) {
        Logger logger = new Logger(
                Application
                        .getInstance()
                        .getLogPath()
        );
        return logger.logError(text);
    }

    /**
     * Добавляет предупреждение в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public static boolean logWarning(String text) {
        Logger logger = new Logger(
                Application
                        .getInstance()
                        .getLogPath()
        );
        return logger.logWarning(text);
    }

    /**
     * Добавляет информацию в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public static boolean logInfo(String text) {
        Logger logger = new Logger(
                Application
                        .getInstance()
                        .getLogPath()
        );
        return logger.logInformation(text);
    }

    /**
     * @return Путь к запущенному .jar файлу
     */
    public static String getExecuteDirectory() {
        String uri = Application.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        String jarPath = URLDecoder.decode(uri, StandardCharsets.UTF_8);
        return jarPath.substring(
                0,
                jarPath.lastIndexOf("/")+1
        );
    }
}
