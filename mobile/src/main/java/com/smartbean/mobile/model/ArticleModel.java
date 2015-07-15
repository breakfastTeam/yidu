package com.smartbean.mobile.model;

import com.smartbean.entity.Article;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public class ArticleModel {
    private DateTime dateTime;
    private List<Article> articles;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
