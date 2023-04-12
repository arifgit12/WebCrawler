package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CNNTestApp {
    static String
            crawlUrl = "https://edition.cnn.com/2023/04/11/politics/alvin-bragg-sues-jim-jordan/index.html";
    public static void main(String[] args) throws IOException {
        System.out.println("CNN Web Crawling Test");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(".layout__content-wrapper");

        for(Element element: elements) {
            String headline = element.select(".headline__wrapper > h1").text();
            System.out.println(headline);

//            Elements categoryHref = element.getElementsByTag("ul");
//            categoryHref.stream().forEach(System.out::println);
//            String categoryText = categoryHref.first().select(".current").text();
//            System.out.println("Category: " + categoryText);

            // Article Published On
            String publishedOn = element.select(".headline__sub-text .timestamp").text();
            System.out.println(publishedOn);


            String authorName = element.select(".headline__sub-text .byline__names a").text();
            System.out.println(authorName);
//
//            String articleImageUrl = element.select(".main-photo").attr("src");
//            System.out.println(articleImageUrl);

            String articleDescription =
                    element.select(".article__content > p").text();

            System.out.println(articleDescription);
        }
    }
}
