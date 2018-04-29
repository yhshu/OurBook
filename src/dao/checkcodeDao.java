package dao;

import java.io.OutputStream;

/*
 *验证码interface
 */
public interface checkcodeDao {
    public String getCertPic(int width, int height, OutputStream os);
}
