package project.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersDaoImp implements MembersDao{
    private NamedParameterJdbcOperations jdbc;

    public void setJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public NamedParameterJdbcOperations getJdbc() {
        return jdbc;
    }

    @Override
    public boolean saveMember(Members newOne) {
        String SQL = "UPDATE lab_members SET name = :name,"
            +"post = :post, photo = :photo, age = :age, event = :event WHERE :id = id;";
        if(newOne.getPhoto() == "")
            SQL = "UPDATE lab_members SET name = :name,"
                    +"post = :post, age = :age, event = :event WHERE :id = id;";
        return doUpdateWork(SQL, newOne);
    }

    @Override
    public boolean insertMember(Members newOne) {
        String SQL = "INSERT INTO lab_members(name, post, age, event, photo) "
                + "VALUE(:name, :post,:age,:event, :photo)";
        return doUpdateWork(SQL, newOne);
    }


    @Override
    public List<String> getMembersNames() {
        String SQL = "SELECT name FROM lab_members";
        List<String> res = jdbc.queryForList(SQL, new HashMap<String, String>() , String.class);
        return res;
    }

    @Override
    public Members getMemberInof(String name) {
        String SQL = "SELECT * FROM lab_members WHERE name=:name";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("name", name);
        List<Members> li = jdbc.query(SQL, mapParams, new RowMa());
        return li.get(0);
    }

    @Override
    public boolean deleteMembers(int id) {
        String SQL = "DELETE FROM lab_members WHERE id = :id";
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("id", id);
        try {
            jdbc.update(SQL, mapParams);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Members> getMembers() {
        String SQL = "SELECT * FROM lab_members";
        Map<String, String> mapParams = new HashMap<String, String>();
        return jdbc.query(SQL, mapParams, new RowMa());
    }

    public static class RowMa implements RowMapper<Members> {
        @Override
        public Members mapRow(ResultSet rs, int rowNum) throws SQLException {
            Members a = new Members();
            a.setAge(rs.getInt("age"));
            a.setEvent(rs.getString("event"));
            a.setId(rs.getInt("id"));
            a.setPhoto(rs.getString("photo"));
            a.setPost(rs.getString("post"));
            a.setName(rs.getString("name"));
            return a;
        }
    }

    public Boolean doUpdateWork(String SQL, Members newOne) {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("name", newOne.getName());
        mapParams.put("post", newOne.getPost());
        mapParams.put("age", newOne.getAge());
        mapParams.put("event", newOne.getEvent());
        mapParams.put("photo", newOne.getPhoto());
        mapParams.put("id", newOne.getId());
        try {
            jdbc.update(SQL, mapParams);
        } catch(Exception e) {
            return false;
        }
        return true;
    }
}
