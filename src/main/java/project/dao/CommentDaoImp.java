package project.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommentDaoImp implements CommentDao{
    private NamedParameterJdbcOperations jdbc;

    public void setJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public NamedParameterJdbcOperations getJdbc() {
        return jdbc;
    }

    @Override
    public List<Comment> getComments(String name) {
        String SQL = "SELECT * FROM lab_comments WHERE articleTitle = '" + name + "';";
        Map<String, String> mapParams = new HashMap<String, String>();
        return jdbc.query(SQL, mapParams, new RowMa());
    }

    @Override
    public Boolean setComment(String title, String comments) {
        String SQL = "INSERT INTO lab_comments(comment, articleTitle) "
                + "VALUE(:comment, :articleTitle)";
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("comment", comments);
        mapParams.put("articleTitle", title);
        try {
            jdbc.update(SQL, mapParams);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    public static class RowMa implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment com = new Comment();
            com.setComment(rs.getString("comment"));
            com.setArticleName(rs.getString("articleTitle"));
            com.setId(rs.getInt("id"));
            return com;
        }
    }
}
