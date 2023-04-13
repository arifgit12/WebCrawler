package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class TelegraphIndiaApp {
    static String
            crawlUrl = "https://www.telegraphindia.com/india/karnataka-government-decision-to-scrap-4-per-cent-muslim-quota-prima-facie-on-highly-shaky-ground-flawed-supreme-court/cid/1929486";
    public static void main(String[] args) throws IOException {
        System.out.println("Telegraph Web Crawling Test");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(".article");

        for(Element element: elements) {
            String headline = element.select(".fs-45 > h1").text();
            System.out.println(headline);

            String subHeadline = element.select(".fs-20").text();
            System.out.println(subHeadline);

            String[] categoryText = crawlUrl.split("/");
            if (categoryText.length>0){
                System.out.println("Category: " + categoryText[categoryText.length-2]);
            }

            // Article Published On
            String publishedDetails = element.select(".fs-12").text();
            System.out.println(publishedDetails);
            String publishedOn = "";
            String authName = "";
            String locationName = "";
            String[] publishedString = publishedDetails.split("\\|");
            if (publishedString.length>0) {
                authName = publishedString[0].trim();
            }
            if (publishedString.length>1){
                locationName=publishedString[1].trim();
            }
            if (publishedString.length>2){
                publishedOn = publishedString[2].trim();
            }

            System.out.println(authName);
            System.out.println(locationName);
            System.out.println(publishedOn);

            Elements imgElements = element.select(".ttdarticlemainimg");
            System.out.println(imgElements.last().select("img[src~=(?i).(png|jpe?g)]").attr("abs:src"));

            String articleDescription =
                    element.select(".fs-17 > p").text();

            System.out.println(articleDescription);
        }
    }
}
