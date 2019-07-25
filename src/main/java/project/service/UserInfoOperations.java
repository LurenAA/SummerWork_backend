package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.dao.UserInfo;
import project.dao.UserInfoDao;
import project.dao.UserInfoDaoImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登陆操作，对应dao的UserInfoDao
 */
@Service
public class UserInfoOperations {
    @Autowired
    private UserInfoDao userInfoDaoImp;

    public void setUserInfoDaoImp(UserInfoDao userInfoDaoImp) {
        this.userInfoDaoImp = userInfoDaoImp;
    }

    public UserInfoDao getUserInfoDaoImp() {
        return userInfoDaoImp;
    }

    public Map<String, Object> login(String account, String passwd) {
        List<UserInfo> userInfosList;
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("state", LoginState.fail);
        userInfosList = userInfoDaoImp.getUserInfo(account, passwd);
        if(userInfosList.size() != 1) {
            return res;
        }
        UserInfo user = userInfosList.get(0);
        res.put("username", user.getUsername());
        if(user.getAccount().equals(account) &&
            user.getPasswd().equals(passwd)) {
            if(user.getDeprecate()) {
                res.put("state", LoginState.deprecate);
                return res;
            }
            res.put("state", LoginState.success);
            return res;
        }
        return res;
    }

    public String getUserName(String account) {
        return userInfoDaoImp.getUserName(account);
    }
}
