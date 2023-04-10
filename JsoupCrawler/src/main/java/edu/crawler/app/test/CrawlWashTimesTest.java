package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlWashTimesTest {
    static final String crawlUrl =
        "https://www.washingtontimes.com/news/2023/apr/10/navy-conducts-warship-passage-s-china-sea-amid-bei/";
    public static void main(String[] args) throws IOException {

        System.out.println("Washington times In Crawler");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select("#main");

        for(Element element: elements) {
            String headline = element.select("#page-title-container h1").text();
            System.out.println(headline);
            String subHeadline = element.select(".page-subheadline").text();
            System.out.println(subHeadline);

            Elements categoryHref = element.getElementsByTag("ul");
            categoryHref.stream().forEach(System.out::println);
            String categoryText = categoryHref.first().select(".current").text();
            System.out.println("Category: " + categoryText);

            // Article Published On
            String publishedOn = element.select(".meta > span.source").text();
            String[] publishedOnText = publishedOn.split("-");
            if(publishedOnText.length>=1) {
                int textLength=publishedOnText.length-1;
                System.out.println(publishedOnText[textLength].trim());
            }

            String publishedLocation = element.select(".storyareawrapper").first().select(".bigtext").select("p").first().text();
            String[] locationText = publishedLocation.split("—");
            if(locationText.length>1)
                System.out.println(locationText[0]);

            String authorName = element.select(".meta > span.byline a").text();
            System.out.println(authorName);

            String articleImageUrl = element.select(".main-photo").attr("src");
            System.out.println(articleImageUrl);

            String articleDescription =
                    element.select(".storyareawrapper").first().select(".bigtext").select("p").text();

            //String articleDescription = element.select(".storyareawrapper");
            System.out.println(articleDescription);
        }
    }
}
