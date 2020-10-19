package ru.kpekepsalt.interfaces;

import java.nio.file.FileSystems;

public interface IHTML {
    /**
     * Сохраняет веб страницу по стандартному пути defaultSavePath
     * @return true в случае успешного сохранения и false в случае ошибки
     */
    boolean savePage();

    /**
     * Сохраняет страницу по заданному пути
     * @param path Путь к сохраненному файлу
     * @return true в случае успешного сохранения и false в случае ошибки
     */
    boolean savePage(String path);

    /**
     * Устанавливает адрес веб страницы
     * @param url Адрес веб страницы
     */
    void setUrl(String url);

    /**
     * Устанавливает путь для сохранения страницы
     * @param path Путь к сохраненному файлу
     */
    void setDownloadPath(String path);

    /**
     * @return Текущий адрес веб страницы
     */
    String getUrl();

    /**
     * @return Размер файла в байтах
     */
    long getFileSize();

    /**
     * @return Название скачанного фаайла
     */
    String getFileName();

    /**
     * @return Путь к скачанному файлу
     */
    String getSavedFilePath();
}
