package project.dao;

import com.alibaba.fastjson.JSON;

import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArticleDaoImp implements ArticleDao{
    private NamedParameterJdbcOperations jdbc;

    public void setJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public NamedParameterJdbcOperations getJdbc() {
        return jdbc;
    }

    @Override
    public boolean saveArticle(Article newOne) {
        String SQL = "INSERT INTO lab_articles(title, author,simpleDes,date,article,columnIds) "
         + "VALUE(:title, :author,:simpleDes,:date,:article,:columnIds)";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("author", newOne.getAuthor());
        mapParams.put("title", newOne.getName());
        mapParams.put("simpleDes", newOne.getSimpleDes());
        mapParams.put("date", newOne.getDate());
        mapParams.put("article", newOne.getArticle());
        mapParams.put("columnIds", "");
        try {
            jdbc.update(SQL, mapParams);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getArticletitles() {
        String SQL = "SELECT title FROM lab_articles";
        List<String> res = jdbc.queryForList(SQL, new HashMap<String, String>() , String.class);
        return res;
    }

    @Override
    public List<Article> getArticleInfos(String Name) {
        String SQL = "SELECT * FROM lab_articles WHERE title=:name";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("name", Name);
        return jdbc.query(SQL, mapParams, new RowMa());
    }

    public static class RowMa implements RowMapper<Article> {

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article a = new Article();
            a.setDate(rs.getString("date"));
            a.setAuthor(rs.getString("author"));
            a.setArticle(rs.getString("article"));
            a.setSimpleDes(rs.getString("simpleDes"));
            a.setName(rs.getString("title"));
            return a;
        }
    }
}
