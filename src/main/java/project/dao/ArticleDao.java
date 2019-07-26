package project.dao;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    boolean saveArticle(Article newOne);
    List<String> getArticletitles();
    List<Article> getArticleInfos(String Name);
}
