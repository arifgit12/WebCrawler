package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TelegraphUKApp {
    static String
            crawlUrl = "https://www.telegraph.co.uk/news/2023/04/15/university-blocks-academic-gender-wars-research/";
    public static void main(String[] args) throws IOException {
        System.out.println("Telegraph UK Web Crawling Test");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select("#main-content");

        for(Element element: elements) {
            //System.out.println(element);
            String headline = element.getElementsByTag("article").select("header > h1.e-headline").text();
            System.out.println(headline);

            String subHeadline = element.getElementsByTag("article").select("header > p.e-standfirst").text();
            System.out.println(subHeadline);

            // Article Published On
            String publishedDetails = element.select(".tpl-article__byline-date").text();
            System.out.println(publishedDetails);

            String authName = element.select(".e-byline__author").text();
            System.out.println(authName);
            String publishedOn = element.select("time.e-published-date").text();
            System.out.println(publishedOn);

            Elements imgElements = element.select(".article-body-image__container");
            System.out.println(imgElements.last().select("img[src~=(?i).(png|jpe?g)]").attr("abs:src"));

            String articleDescription =
                    element.getElementsByTag("article")
                            .select("div.articleBodyText > div.article-body-text > p").text();

            System.out.println(articleDescription);
        }
    }
}
