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
        String SQL = "INSERT INTO lab_articles(title, author,simpleDes,date,article) "
         + "VALUE(:title, :author,:simpleDes,:date,:article)";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("author", newOne.getAuthor());
        mapParams.put("title", newOne.getName());
        mapParams.put("simpleDes", newOne.getSimpleDes());
        mapParams.put("date", newOne.getDate());
        mapParams.put("article", newOne.getArticle());
//        mapParams.put("columnIds", "");
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

    @Override
    public boolean setArticleColumn(List<String> s, int col) {
        String coll = "columnId" + col;
        String SQL = "UPDATE lab_articles SET " + coll + "= ''";
        Map<String, Object> mapParams = new HashMap<String, Object>();
        try {
            jdbc.update(SQL, mapParams);
        } catch(Exception e) {
            return false;
        }
        String whe = "";
        for(int i = 0;i < s.size(); i++) {
            if (i != s.size() - 1) {
                whe += s.get(i) + "|";
            } else {
                whe += s.get(i) + "";
            }
        }
        SQL = "UPDATE lab_articles SET " + coll +" = 'true' WHERE title REGEXP '" + whe + "'";
        try {
            System.out.println(jdbc.update(SQL, mapParams));
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getArticleNamesByCol(int col) {
        String SQL = "SELECT title FROM lab_articles WHERE columnId" + col + " = 'true'";
        Map<String, String> mapParams = new HashMap<String, String>();
        return jdbc.queryForList(SQL, mapParams, String.class);
    }

    @Override
    public List<Article> getArticleContentByCol(int col) {
        String SQL = "SELECT * FROM lab_articles WHERE columnId" + col + " = 'true'";
        if(col == -1) {
            SQL = "SELECT * FROM lab_articles";
        }
        Map<String, String> mapParams = new HashMap<String, String>();
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
