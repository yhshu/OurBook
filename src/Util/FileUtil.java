package Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileUtil {
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list(); // 递归删除目录中的子目录下
            if (children != null) {
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 判断字符串是否只包含字母、数字和汉字
     *
     * @param str 字符串
     * @return 包含特殊符号false 不包含true
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    /**
     * 将内容写入文件，若文件名重复，在末尾自动加数字以示区分
     *
     * @param file    可能存在文件名重复的文件
     * @param content 内容
     * @return 写入的文件名
     * @throws IOException 写入失败
     */
    public static String write(File file, String content) throws IOException {
        // 文件的完整名称
        String filename = file.getPath();
        // 无后缀文件名
        String name = filename.substring(0, filename.indexOf("."));
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        File parentDir = new File(file.getParent());
        if (!parentDir.exists()) if (!parentDir.mkdir()) return null;
        int i = 1;
        // 若文件名重复，在后缀前添加数字
        while (file.exists()) {
            String newFilename = name + "(" + i + ")" + suffix;
            file = new File(newFilename);
            i++;
        }
        if (!file.createNewFile())  //新建文件
            return null;
        PrintStream printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");
        printStream.print(content);
        printStream.close();
        return file.getPath();
    }
}
