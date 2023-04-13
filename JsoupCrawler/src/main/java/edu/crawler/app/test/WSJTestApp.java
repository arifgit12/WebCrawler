package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WSJTestApp {
    static String
            crawlUrl = "https://www.wsj.com/articles/intelligence-leak-spotlights-russian-infighting-wagners-role-in-ukraine-war-c0f14632";
    public static void main(String[] args) throws IOException {
        System.out.println("WSJ Web Crawling Test");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(".article-container");

        for(Element element: elements) {
            String headline = element.select(".article-header > h1").text();
            System.out.println(headline);

            String[] categoryText = crawlUrl.split("/");
            if (categoryText.length>6){
                System.out.println("Category: " + categoryText[6]);
            }

            // Article Published On
            String publishedOn = element.select(".headline__sub-text .timestamp").text();
            System.out.println(publishedOn);

            String authorName = element.select(".headline__sub-text .byline__names a").text();
            System.out.println(authorName);

//            Elements imgElements = element.select(".article__main .image__container .image__picture");
//            System.out.println(imgElements.last().select("img[src~=(?i).(png|jpe?g)]").attr("abs:src"));

            String articleDescription =
                    element.select(".article__content > p").text();
            Elements articles = elements.select("article");
            Elements sections = articles.select("section");
            sections.stream().forEach(System.out::println);

            System.out.println(articleDescription);
        }
    }
}
