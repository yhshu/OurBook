package service;

public interface UserService {
    /**
     * 用户注册
     *
     * @param nickname 用户名
     * @param password 密码
     */
    public void register(String nickname, String password);

    /**
     * 用户登录
     *
     * @param nickname 用户名
     * @param password 密码
     */
    public void login(String nickname, String password);
}
