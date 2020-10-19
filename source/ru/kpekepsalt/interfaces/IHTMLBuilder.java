package ru.kpekepsalt.interfaces;

public interface IHTMLBuilder {

    /**
     * Устанавливает адрес веб страницы
     * @param url Адрес веб страницы
     */
    IHTMLBuilder setUrl(String url);

    /**
     * Устанавливает путь для скачивания файла
     * @param path Путь для скачивания файла
     */
    IHTMLBuilder setDownloadPath(String path);

    /**
     * @return экземпляр объекта
     */
    IHTML build();
}
