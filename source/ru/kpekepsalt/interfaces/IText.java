package ru.kpekepsalt.interfaces;

import java.util.Map;

public interface IText {

    /**
     * Подсчитывает уникальные слова в тексте
     * @return Словарь вхождений слов (слово->количество)
     */
    Map<String, Integer> getWordsStats();

}
