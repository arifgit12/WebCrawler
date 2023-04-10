package edu.crawler.app;

import edu.crawler.app.configuration.TheHinduTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CrawlTheHinduTest {
    static final String crawlUrl = "https://www.thehindu.com/news/international/uk-pm-sunak-to-meet-us-president-in-northern-ireland/article66718264.ece";
    public static void main(String[] args) throws IOException {

        System.out.println("The Hindu In Crawler");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(TheHinduTag.mainTag);

        for(Element element: elements) {
            String headline = element.select(TheHinduTag.titleTag).text();
            System.out.println(headline);
            String subHeadline = element.select(TheHinduTag.subHeadlineTag).text();
            System.out.println(subHeadline);

            // Article Published On
            String publishedOn = element.select(TheHinduTag.publishedOnTag).text();
            System.out.println(publishedOn);

            String publishedLocation = element.select(TheHinduTag.publishedLocationTag).text();
            System.out.println(publishedLocation);

            String authorName = element.select(TheHinduTag.authorTag).text();
            System.out.println(authorName);

            String articleImageUrl = element.select(TheHinduTag.imageSrcTag).attr("src");
            System.out.println(articleImageUrl);

            String articleDescription = element.select(TheHinduTag.bodyTag).text();
            System.out.println(articleDescription);
        }
    }
}
