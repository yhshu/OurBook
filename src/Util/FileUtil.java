package Util;

import org.apache.commons.fileupload.FileItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileUtil {

    private static final String[] allowedExt = new String[]{"jpg", "jpeg", "gif", "png", "bmp"};

    public static boolean deleteDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list(); // 递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(path + "/" + child);
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
        // 无后缀的文件名
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
        if (!file.createNewFile())  // 新建文件
            return null;
        PrintStream printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");
        printStream.print(content);
        printStream.close();
        return file.getPath();
    }

    /**
     * 删除文件
     *
     * @param filePath  文件绝对路径
     * @param errorText 删除失败提示
     * @return 删除成功 true 删除失败 false
     */
    public static boolean delete(String filePath, String errorText) {
        try {
            System.gc();
            if (!new File(filePath).delete()) throw new Exception();
        } catch (Exception e) {
            System.out.println(errorText);
            return false;
        }
        return true;
    }

    /**
     * 上传图片，能够检查出后缀名被修改的文件并删除
     *
     * @param fm       客户端发送的文件
     * @param filePath 文件将要存储在服务器上的路径
     * @return
     */
    public static int uploadImage(FileItem fm, String filePath) {
        int maxsize = 2 * 1024 * 1024;
        boolean isImage = false;
        // 先检查后缀
        for (String ext : FileUtil.allowedExt) {
            if (fm.getContentType().contains(ext)) {
                isImage = true;
                break;
            }
        }
        if (!isImage) {
            return 415;
        }
        if (fm.getSize() > maxsize) {
            return 403;
        }
        // 向文件中写入数据
        File saveFile = new File(filePath);
        try {
            fm.write(saveFile);
        } catch (Exception e) {
            return 520;
        }
        // 再检查文件后缀名是否被修改，如果是，立即删除
        try {
            Image img = ImageIO.read(saveFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                if (saveFile.delete()) System.out.println("删除可疑文件成功");
                else System.out.println("删除可疑文件失败");
                return 415;
            }
        } catch (Exception e) {
            if (saveFile.delete()) System.out.println("删除可疑文件成功");
            else System.out.println("删除可疑文件失败");
            return 415;
        }
        return 200;
    }
}
