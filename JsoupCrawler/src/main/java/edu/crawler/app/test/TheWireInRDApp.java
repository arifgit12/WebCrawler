package edu.crawler.app.test;

import edu.crawler.app.model.TheWireIn;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class TheWireInRDApp {
    private final static String articleUrl ="https://thewire.in/law/bjp-mla-demand-to-demolish-taj-mahal-qutub-minar";
    public static void main(String[] args) throws IOException {
        System.out.println("The Wire In Crawler");

        String mainTag = "#wire-main-content";

        String bodyTag=".grey-text";
        String titleTag="div>h1.title";
        String subHeadlineTag="div>p.shortDesc";
        String urlTag = ".featured-image>img[src$=.png]";
        //String authorTag = ".author__name";
        String authorTag = ".author__name";
        String publishedOnTag = "span.posted-on";

        Document document = Jsoup.connect(articleUrl).get();
        Elements elements = document.select(mainTag);

        for(Element element: elements) {
            String headline = element.select(titleTag).text();
            System.out.println(headline);
            String subHeadline = element.select(subHeadlineTag).text();
            System.out.println(subHeadline);
            Elements categoryHref = element.getElementsByTag("a");
            String categoryText = categoryHref.first().text();
            System.out.println(categoryText);

            String authorName = element.select(authorTag).text();
            System.out.println(authorName);
            Elements authorHref = element.select(authorTag).first().getElementsByTag("a");
            System.out.println(authorHref.first().text());
            String authorNameUrl = element.select(authorTag + " a").attr("href");
            System.out.println(authorNameUrl);
            String authorNameText = element.select(authorTag + " a").text();
            System.out.println(authorNameText);
            Elements images = element.select(urlTag);
            System.out.println(images);
            String imageUrl = images.first().absUrl("src");
            System.out.println(imageUrl);
            String srcValue = images.attr("src");
            System.out.println(srcValue);
            String articleImageUrl = element.select(".featured-image img").attr("src");
            System.out.println(articleImageUrl);

            // Article Published On
            String publishedOn = element.select(publishedOnTag).text();
            System.out.println(publishedOn);

            String publishedLocation = element.select("div.grey-text > p").select("strong").text();
            System.out.println(publishedLocation);

            String articleDescription = element.select(bodyTag).text();
            System.out.println(articleDescription);

            setArticelDetail(headline, subHeadline, categoryText, authorName, articleImageUrl, publishedOn, publishedLocation, articleDescription);
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
