package project.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * 操作用户信息接口
 */
public interface UserInfoDao {
    List<UserInfo> getUserInfo(String account, String passwd);
    String getUserName(String account);
}
