package ru.kpekepsalt.interfaces;

public interface ILogger {

    /**
     * Префикс ошибки
     */
    String ERROR_PREFIX = "[ERROR] ";
    /**
     * Префикс предупреждения
     */
    String WARNING_PREFIX = "[WARN] ";
    /**
     * Префикс информации
     */
    String INFO_PREFIX = "[INFO] ";

    /**
     * @param text Текст для записи
     * @param append Очищать лог перед записью
     * @return true в случае успешной записи и false в случае ошибки
     */
    boolean log(String text, boolean append);

}
