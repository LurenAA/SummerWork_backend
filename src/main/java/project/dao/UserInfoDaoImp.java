package project.dao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  操作用户信息的dao实现
 *
 */
@Component
public class UserInfoDaoImp implements UserInfoDao{

    private NamedParameterJdbcOperations jdbc;

    public void setJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    public NamedParameterJdbcOperations getJdbc() {
        return jdbc;
    }

    @Override
    public List<UserInfo> getUserInfo(String account, String passwd) {
        String SQL = "SELECT * FROM lab_users " +
                "WHERE account=:account AND passwd=:passwd";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("account", account);
        mapParams.put("passwd", passwd);
        return  jdbc.query(SQL, mapParams, new RowMapperForUserInfo());
    }

    @Override
    public String getUserName(String account) {
        String SQL = "SELECT username FROM lab_users " +
                "WHERE account=:account";
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("account", account);
        Map<String,Object> res =jdbc.queryForMap(SQL, mapParams);
        if(res.size() != 0) {
            return (String)res.get("username");
        }
        return null;
    }

    private static class RowMapperForUserInfo implements RowMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserInfo uf = new UserInfo();
            uf.setAccount(rs.getString("account"));
            uf.setDeprecate(rs.getBoolean("deprecate"));
            uf.setLevel(rs.getBoolean("level"));
            uf.setPasswd(rs.getString("passwd"));
            uf.setUsername(rs.getString("username"));
            return uf;
        }
    }
}
