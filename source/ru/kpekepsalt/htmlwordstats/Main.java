package ru.kpekepsalt.htmlwordstats;

import ru.kpekepsalt.interfaces.IHTML;
import ru.kpekepsalt.interfaces.IHTMLBuilder;

import java.util.*;

public class Main {

    public static void main(String[] args){
        Application app = Application.getInstance();
        Map<String, List<String>> params = app.getCLIParams(args);
        IHTMLBuilder htmlBuilder = new HTMLBuilder();
        List<String> paramValues;

        paramValues = params.get("u");
        if (paramValues == null) {
            System.out.println("Введите адрес веб страницы");
            Scanner sc = new Scanner(System.in);
            htmlBuilder = htmlBuilder.setUrl(
                    sc.nextLine()
            );
        } else {
            htmlBuilder = htmlBuilder.setUrl(
                    paramValues.get(0)
            );
        }

        paramValues = params.get("dp");
        if (paramValues != null) {
            htmlBuilder = htmlBuilder.setDownloadPath(
                    paramValues.get(0)
            );
        }

        paramValues = params.get("l");
        if (paramValues != null) {
            app.setLogPath(
                    paramValues.get(0)
            );
        }

        IHTML html = htmlBuilder.build();
        if (html.savePage()) {
            TextTool textTool = new TextTool();
            Map<String, Integer> wordStats = textTool.getWordsStatsFromFile(
                    html.getSavedFilePath()
            );
            wordStats.forEach(
                    (word, count) ->
                            System.out.println(word + " - " + count)
            );
        }
    }


}
