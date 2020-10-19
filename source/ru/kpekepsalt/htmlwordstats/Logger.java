package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.ILogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements ILogger {

    /**
     * Путь к файлу лога
     */
    private final String logPath;

    /**
     * @param path Путь к файлу лога
     */
    public Logger(String path) {
        logPath = path;
    }

    /**
     * Добавляет запись в лог
     * @param text Текст для записи
     * @param append Очищать лог перед записью
     * @return true в случае успешной записи и false в случае ошибки
     */
    @Override
    public boolean log(String text, boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            logPath,
                            append
                    )
            );
            writer.write(text+"\n");
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Возникла ошибка при записи в лог: "
                    +e.getLocalizedMessage()
            );
            return false;
        }
    }

    /**
     * Добавляет запись в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public boolean log(String text) {
        return log(text, true);
    }

    /**
     * Добавляет ошибку в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public boolean logError(String text) {
        return log(ERROR_PREFIX +text);
    }

    /**
     * Добавляет предупреждение в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public boolean logWarning(String text) {
        return log(WARNING_PREFIX +text);
    }

    /**
     * Добавляет информацию в лог
     * @param text Текст для записи
     * @return true в случае успешной записи и false в случае ошибки
     */
    public boolean logInformation(String text) {
        return log(INFO_PREFIX +text);
    }

}
