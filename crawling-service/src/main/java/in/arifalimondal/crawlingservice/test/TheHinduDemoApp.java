package in.arifalimondal.crawlingservice.test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static in.arifalimondal.crawlingservice.test.StringUtil.PrintStatement;

public class TheHinduDemoApp {
    static final String crawlUrl = "https://www.thehindu.com/news/national/ayodhya-mosque-to-be-better-than-the-taj-mahal/article67737843.ece";
    public static void main(String[] args) throws IOException {

        System.out.println("The Hindu In Crawler");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select(".container");

        for(Element element: elements) {
            String headline = element.select(".storyline").text();
            PrintStatement("Headline", headline);
            String subHeadline = element.select("h3.sub-title").text();
            PrintStatement("Sub Headline", subHeadline);

            // Article Published On
            String publishedOn = element.select(".publish-time").text();
            PrintStatement("Published On", publishedOn);

            String publishedLocation = element.select(".publish-time").text();
            PrintStatement("Location", publishedLocation);

            String authorName = element.select(".author-name").text();
            PrintStatement("Author", authorName);

            String articleImageUrl = element.select(".lead-img").attr("src");
            PrintStatement("Image", articleImageUrl);

            String articleDescription = element.select(".articlebodycontent ").text();
            PrintStatement("Content", articleDescription);
        }
    }

}
