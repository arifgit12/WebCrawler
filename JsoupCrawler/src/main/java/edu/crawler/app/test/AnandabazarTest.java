package edu.crawler.app.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AnandabazarTest {
    static String
            crawlUrl = "https://www.anandabazar.com/west-bengal/trinamool-mla-jiban-krishna-sahas-performance-in-the-assembly-is-not-good-at-all-dgtl/cid/1422919";
    public static void main(String[] args) throws IOException {
        System.out.println("Anandabazar Web Crawling Test");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(".articlebox");

        for(Element element: elements) {
            String headline = element.select("h1").text();
            System.out.println(headline);

            String subHeadline = element.select("h2").text();
            System.out.println(subHeadline);

            String[] categoryText = crawlUrl.split("/");
            if (categoryText.length>0){
                System.out.println("Category: " + categoryText[categoryText.length-2]);
            }

            // Article Published On
            String publishedDetails = element.select(".editbox").text();
            System.out.println(publishedDetails);

            String authName = element.select(".editbox > h5").text();
            System.out.println(authName);

            String publishedOn = element.select(".editbtm > span:contains('২০২৩')").text();
            System.out.println(publishedOn);

            String publishedLocation = element.select(".editbtm").text();
            System.out.println(publishedLocation);

            Elements imgElements = element.select(".leadimgbox");
            System.out.println(imgElements.last().select("img[src~=(?i).(png|jpe?g)]").attr("abs:src"));

            String articleDescription =
                    element.select(".contentbox > p").text();

            System.out.println(articleDescription);
        }
    }
}
