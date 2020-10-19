package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.IHTML;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTML implements IHTML {

    /**
     * Адрес веб страницы
     */
    private String url;
    /**
     * Путь для сохранения страницы
     */
    private String downloadPath;
    /**
     * Название сохраненного файла
     */
    private String filename;
    /**
     * Размер скачанного файла
     */
    private long fileSize = -1;

    public HTML() {
        this.downloadPath = Application.getExecuteDirectory();
    }

    /**
     * Задает адрес веб страницы
     * @param url Адрес веб страницы
     */
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Возвращает
     * @return Адрес веб страницы
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Сохраняет страницу, испольуя путь по умолчанию
     */
    @Override
    public boolean savePage() {
        return savePage(downloadPath);
    }

    /**
     * Сохраняет страницу по указанному пути
     * @param path Путь к сохраненному файлу
     */
    @Override
    public boolean savePage(String path) {
        setFileSize(-1);
        try {
            String filename = this.url
                    .replace("http://","")
                    .replace("https://", "")
                    .replace("www.","");
            int haveSlash = filename.indexOf("/");

            if (haveSlash != -1) {
                filename = filename.substring(0, haveSlash);
            }

            filename += ".html";
            setFilename(filename);

            if (!path.endsWith("/")) {
                path = path + "/";
            }

            path += filename;

            URL url = new URL(this.url);
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()
                            )
                    );
            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(path)
                    );
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            reader.close();
            writer.close();
            setFileSize(
                    Files.size(
                            Paths.get(path)
                    )
            );
            System.out.println("Страница успешно сохранена "
                    +getSavedFilePath()
            );
            return true;
        }
        catch (UnknownHostException uh) {
            System.out.println("Указанная веб страница недоступна");
            Application.logError(url
                    +" веб страница недоступна"
            );
            return false;
        }
        catch (MalformedURLException mue) {
            System.out.println("Неверно указан адрес веб страницы");
            Application.logError("Ошибка при сохранении страницы "
                    +url
                    +" - неверно указан адрес веб страницы"
            );
            return false;
        }
        catch (IOException ie) {
            System.out.println("Ошибка при сохранении страницы на диск - "
                    +ie.getMessage()
            );
            Application.logError("Ошибка при сохранении страницы на диск - "
                    +ie.getMessage()
            );
            return false;
        }
    }

    /**
     * Устанавливает путь для сохранения страницы
     * @param downloadPath Путь для сохранения страницы
     */
    @Override
    public void setDownloadPath(String downloadPath) {
        if (!downloadPath.endsWith("/")) {
            downloadPath += "/";
        }
        this.downloadPath = downloadPath;
    }

    /**
     * @return Текущий путь для сохранения страницы
     */
    public String getDownloadPath() {
        return downloadPath;
    }

    protected void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Возвращает размер страницы или -1, если страницы не была сохранена
     * @return размер страницы
     */
    @Override
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Возвращает название сохраненной страницы
     * @return название файла
     */
    @Override
    public String getFileName() {
        return filename;
    }

    protected void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Возвращает путь к сохраненной странице
     * @return путь к сохраненной странице
     */
    @Override
    public String getSavedFilePath() {
        return downloadPath + filename;
    }

}
