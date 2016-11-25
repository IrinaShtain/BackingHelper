package com.shtainyky.backinghelper.utils;

import android.util.Log;

import com.shtainyky.backinghelper.model.ThemeItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parsing {

    public static List<ThemeItem> parsingResult(int position) {
        List<ThemeItem> list = new ArrayList<>();

        if (position >= 0 && position < 4) {
            String urlPokerStrategy = null;
            switch (position) {
                case 0:
                    urlPokerStrategy = "https://ru.pokerstrategy.com/forum/board.php?boardid=413";
                    break;
                case 1:
                    urlPokerStrategy = "https://ru.pokerstrategy.com/forum/board.php?boardid=387";
                    break;
                case 2:
                    urlPokerStrategy = "https://ru.pokerstrategy.com/forum/board.php?boardid=388";
                    break;
                case 3:
                    urlPokerStrategy = "https://ru.pokerstrategy.com/forum/board.php?boardid=389";
                    break;
            }

            try {
                Document document = Jsoup.connect(urlPokerStrategy).get();
                Elements elements_forumthreads = document
                        .select(".forumthreads__content").last()
                        .select(".titleBox");
                for (Element element : elements_forumthreads) {

                    Element elementUser = element
                            .select(".forum--board--thread-overview-threadStarter").first();
                    Element elementTitle = element.getElementsByClass("forum--board-thread-overview-hasUnreadPosts").first();
                    Element element_link = element.select("a[href]").first();
                    Element element_views = element.select("p").first();


                    if (elementTitle != null) {
                        String[] stringsViews = element_views.text().split(" ");
                        String numberViews = "";
                        char[] numbers = stringsViews[0].toCharArray();
                        for (int i = 0; i < numbers.length; i++) {
                            if (numbers[i] != '.')
                                numberViews = numberViews + numbers[i];
                        }

                        ThemeItem themeItem = new ThemeItem();
                        themeItem.setTitle(elementTitle.text());
                        themeItem.setLink(element_link.attr("abs:href"));
                        themeItem.setUser(elementUser.text());
                        themeItem.setViews(Integer.parseInt(numberViews));
                        list.add(themeItem);
//                        Log.i("mLog", elementUser.text());
//                        Log.i("mLog", elementTitle.text());
//                        Log.i("mLog", element_link.attr("abs:href"));
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("mLog", "===========Parsing===0--4================position========= " + position);
        } else {
            String urlGipsyTeam = null;
            switch (position) {
                case 4:
                    urlGipsyTeam = "http://forum.gipsyteam.ru/backing/forum?fid=43";
                    break;
                case 5:
                    urlGipsyTeam = "http://forum.gipsyteam.ru/backing/forum?fid=75";
                    break;
            }
            try {
                Document document = Jsoup.connect(urlGipsyTeam).get();
                Elements elements = document.select(".new");
                for (Element element : elements) {
                    Element element_title_link = element.select("h2").first().select("a").first();
                    Element element_user = element.select(".user").first();
                    Element element_views = element.select(".read").first();
                    String[] stringsViews = element_views.text().split(" ");

                    ThemeItem themeItem = new ThemeItem();
                    themeItem.setTitle(element_title_link.text());
                    themeItem.setLink(element_title_link.attr("abs:href"));
                    themeItem.setUser(element_user.text());
                    themeItem.setViews(Integer.parseInt(stringsViews[0]));

                    list.add(themeItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("mLog", "===========Parsing 5 6=============position ======================== " + position);
        }
        return list;
    }
}
