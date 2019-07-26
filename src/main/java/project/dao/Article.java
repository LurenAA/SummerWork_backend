package project.dao;

import java.util.List;

/**
 * 文章
 */
public class Article {
    private String name,author,simpleDes,date,article,columnIds;

    public void setArticle(String article) {
        this.article = article;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSimpleDes(String simpleDes) {
        this.simpleDes = simpleDes;
    }

    public void setColumnIds(String columnIds) {
        this.columnIds = columnIds;
    }

    public String getColumnIds() {
        return columnIds;
    }

    public String getArticle() {
        return article;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSimpleDes() {
        return simpleDes;
    }
}
