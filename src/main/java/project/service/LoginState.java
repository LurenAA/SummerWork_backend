package project.service;

/**
 * 记录登陆状态，成功，失败和被禁
 */
public enum LoginState {
    success(11), fail(12), deprecate(13);
    private int i;
    private LoginState(int i) {
        this.i = i;
    }
    public int getLoginState() {
        return i;
    }
}