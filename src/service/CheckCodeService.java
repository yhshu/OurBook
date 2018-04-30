package service;

import java.io.OutputStream;

public interface CheckCodeService {
    String getCertPic(int width, int height, OutputStream os);
}
