package project.dao;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    boolean saveArticle(Article newOne);
    List<String> getArticletitles();
    List<Article> getArticleInfos(String Name);
    boolean setArticleColumn(List<String> s, int col);
    List<String> getArticleNamesByCol(int col);
    List<Article> getArticleContentByCol(int col);
}
