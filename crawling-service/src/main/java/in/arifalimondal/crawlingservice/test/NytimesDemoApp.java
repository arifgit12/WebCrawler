package in.arifalimondal.crawlingservice.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static in.arifalimondal.crawlingservice.test.StringUtil.PrintStatement;

public class NytimesDemoApp {
    static final String crawlUrl = "https://www.nytimes.com/2024/01/14/us/politics/biden-democrats-campaign-base-delaware.html";
    public static void main(String[] args) throws IOException {
        System.out.println("The New York Times In Crawler");

        Document document = Jsoup.connect(crawlUrl).get();
        Elements elements = document.select("article#story.css-1vxca1d.e1lmdhsb0");

        for(Element element: elements) {
            String headline = element.select("h1#link-1b79859b.css-1l8buln.e1h9rw200").text();
            PrintStatement("Headline", headline);
            String subHeadline = element.select("p#article-summary").text();
            PrintStatement("Sub Headline", subHeadline);

//            String publishedOn = element.select(".publish-time").text();
//            PrintStatement("Published On", publishedOn);
//
//            String publishedLocation = element.select(".publish-time").text();
//            PrintStatement("Location", publishedLocation);
//
//            String authorName = element.select(".author-name").text();
//            PrintStatement("Author", authorName);

            String articleImageUrl = element.select(".css-rq4mmj").attr("src");
            PrintStatement("Image", articleImageUrl);

            String articleDescription = element.select("section[name=articleBody].meteredContent.css-1r7ky0e").text();
            PrintStatement("Content", articleDescription);
        }

    }
}
