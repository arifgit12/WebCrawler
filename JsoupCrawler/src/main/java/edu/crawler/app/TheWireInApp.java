package edu.crawler.app;

import edu.crawler.app.configuration.TheWireInTag;
import edu.crawler.app.configuration.TheWireInUrls;
import edu.crawler.app.model.TheWireIn;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class TheWireInApp {
    public static void main(String[] args) throws IOException {
        System.out.println("The Wire In Crawler");
        for (String url: TheWireInUrls.urls) {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(TheWireInTag.mainTag);

            for(Element element: elements) {
                String headline = element.select(TheWireInTag.titleTag).text();
                String subHeadline = element.select(TheWireInTag.subHeadlineTag).text();
                Elements categoryHref = element.getElementsByTag("a");
                String categoryText = categoryHref.first().text();
                String authorName = element.select(TheWireInTag.authorTag).text();
                String articleImageUrl = element.select(TheWireInTag.imageSrcTag).attr("src");
                // Article Published On
                String publishedOn = element.select(TheWireInTag.publishedOnTag).text();
                String publishedLocation = element.select(TheWireInTag.publishedLocationTag).select("strong").text();
                String articleDescription = element.select(TheWireInTag.bodyTag).text();
                TheWireIn wireDetails = setArticelDetail(headline, subHeadline, categoryText, authorName, articleImageUrl, publishedOn, publishedLocation, articleDescription);

                System.out.println(wireDetails);
            }
        }
    }

    static TheWireIn setArticelDetail(String headline, String subHeadline, String categoryText, String authorName, String articleImageUrl, String publishedOn, String publishedLocation, String articleDescription) {
        TheWireIn wire = new TheWireIn();
        wire.setHeadLine(headline);
        wire.setSubHeadLine(subHeadline);
        wire.setAuthor(authorName);
        wire.setPublishedOn(publishedOn);
        wire.setPublishedLocation(publishedLocation);
        wire.setCategory(categoryText);
        wire.setArticleImageUrl(articleImageUrl);
        wire.setArticle(articleDescription);
        wire.setCrawlOn(new Date());
        return wire;
    }
}
