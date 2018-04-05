package service;

public interface UserService {
    /**
     * 用户注册
     * 用户信息插入数据库，注册成功后跳转到登录页
     *
     * @param nickname 用户名
     * @param password 密码
     */
    public void register(String nickname, String password);

    /**
     * 用户登录
     * 核对用户名密码，若登录成功跳转到个人主页，并发送cookie
     *
     * @param nickname 用户名
     * @param password 密码
     */
    public void login(String nickname, String password);
}
