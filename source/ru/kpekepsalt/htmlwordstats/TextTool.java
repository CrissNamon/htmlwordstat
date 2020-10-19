package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.IText;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTool implements IText {

    private String text;

    public TextTool(){}

    public TextTool(String text) {
        this.text = text;
    }

    /**
     * Возвращает текст, видимый в браузере
     * @param text Строка для очистки
     * @return Очищенный текст
     */
    public static String removeHTML(String text) {
        /*
        Удаляем <head>
         */
        int headStart = text.indexOf("<head");
        int headEnd = text.indexOf("<body");
        if (headStart != -1) {
            text = text.substring(headStart);
            if (headEnd == -1) {
                text = "";
            } else {
                text = text.replaceAll("(?m)<head[^>]*>.*</head>", "");
            }
        } else {
            if (headEnd != -1) {
                text = text.substring(0, headEnd);
            }
        }
        /*
        Удаляем все теги
         */
        text = text.replaceAll("(?m)<script[^>]*>(.*?)</script>", " ");
        text = text.replaceAll("(?m)<style[^>]*>(.*?)</style>", " ");
        text = text.replaceAll("(?m)<!--(\\D|\\s)+-->", " ");
        text = text.replaceAll("<[^>]*>", " ");
        text = text.replaceAll("(?>&\\b\\w+\\b;)", " ");
        return text;
    }

    /**
     * Подсчитывает уникальные слова в тексте
     * @return Словарь вхождений слов (слово->количество)
     */
    @Override
    public Map<String, Integer> getWordsStats() {
        Map<String, Integer> wordStats = new TreeMap<>();
        String text = removeHTML(this.text);
        final String regex = "[\\p{Alpha}][-']?[\\p{Alpha}-']+|(?<![-])[\\p{Alpha}]";
        final Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        final Matcher matcher = pattern.matcher(text);
        String word;
        while (matcher.find()) {
            word = matcher.group().toUpperCase();
            wordStats.put(
                    word,
                    wordStats.getOrDefault(word, 0)+1
            );
        }
        return wordStats;
    }

    /**
     * Подсчитывает уникальные слова в тексте
     * @param text Текст для подсчета слов
     * @return Словарь вхождений слов (слово->количество)
     */
    public Map<String, Integer> getWordsStats(String text) {
        this.text = text;
        return getWordsStats();
    }

    /**
     * Подсчитывает уникальные слова в тексте из файла
     * @param filePath Путь к файлу
     * @return Словарь вхождений слов (слово->количество)
     */
    public Map<String, Integer> getWordsStatsFromFile(String filePath) {
        Map<String, Integer> wordStats = new TreeMap<>();
        Map<String, Integer> wordStatsLocal;
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            try (Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();

                    wordStatsLocal = getWordsStats(
                           line
                    );
                    wordStatsLocal.forEach(
                            (word, count) -> wordStats.merge(word, count, Integer::sum)
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла "+filePath);
            Application.logError("Ошибка при чтении файла "+filePath+". "+e.getLocalizedMessage());
        }
        return wordStats;
    }
}
