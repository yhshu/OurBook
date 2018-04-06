package service;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param name          书名
     * @param description   书的简介（可选）
     * @param chiefEditorID 主编用户编号
     */
    void add(String name, String description, int chiefEditorID);
}