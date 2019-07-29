package project.dao;

public class Comment {
    private String comment, articleName;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getComment() {
        return comment;
    }
}
