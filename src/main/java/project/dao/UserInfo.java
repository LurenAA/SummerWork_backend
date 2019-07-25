package project.dao;

/**
 * 登陆时用户信息类
 */
public class UserInfo {
    private String account, passwd, username;
    private boolean deprecate, level;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getAccount() {
        return account;
    }

    public boolean getLevel() {
        return level;
    }

    public boolean getDeprecate() {
        return deprecate;
    }
}
