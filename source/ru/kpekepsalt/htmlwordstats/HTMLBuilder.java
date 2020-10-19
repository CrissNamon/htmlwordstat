package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.IHTML;
import ru.kpekepsalt.interfaces.IHTMLBuilder;

public class HTMLBuilder implements IHTMLBuilder {

    private final IHTML html;

    public HTMLBuilder() {
        html = new HTML();
    }

    /**
     * Устанавливает адрес веб страницы
     * @param url Адрес веб страницы
     */
    @Override
    public IHTMLBuilder setUrl(String url) {
        html.setUrl(url);
        return this;
    }

    /**
     * Устанавливает путь для скачивания файла
     * @param path Путь для скачивания файла
     */
    @Override
    public IHTMLBuilder setDownloadPath(String path) {
        html.setDownloadPath(path);
        return this;
    }

    /**
     * @return экземпляр объекта
     */
    @Override
    public IHTML build() {
        return html;
    }

}
