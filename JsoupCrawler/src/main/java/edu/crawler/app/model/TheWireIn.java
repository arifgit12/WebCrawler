package edu.crawler.app.model;

import java.util.Date;

public class TheWireIn {
    private String headLine;
    private String subHeadLine;
    private String author;
    private String publishedOn;
    private String publishedLocation;
    private String article;
    private String articleImageUrl;
    private String category;
    private Date crawlOn;

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getSubHeadLine() {
        return subHeadLine;
    }

    public void setSubHeadLine(String subHeadLine) {
        this.subHeadLine = subHeadLine;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getPublishedLocation() {
        return publishedLocation;
    }

    public void setPublishedLocation(String publishedLocation) {
        this.publishedLocation = publishedLocation;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public void setArticleImageUrl(String articleImageUrl) {
        this.articleImageUrl = articleImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCrawlOn() {
        return crawlOn;
    }

    public void setCrawlOn(Date crawlOn) {
        this.crawlOn = crawlOn;
    }

    @Override
    public String toString() {
        return "TheWireIn: { " +
                "HeadLine='" + headLine + '\'' +
                ", SubHeadLine='" + subHeadLine + '\'' +
                ", Author='" + author + '\'' +
                ", Published On='" + publishedOn + '\'' +
                ", Location='" + publishedLocation + '\'' +
                ", ImageUrl='" + articleImageUrl + '\'' +
                ", Category='" + category + '\'' +
                ", Crawled On='" + crawlOn + '\'' +
                ", Article='" + article + '\'' +
                '}';
    }
}
