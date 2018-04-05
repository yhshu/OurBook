package service;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param name        // 书名
     * @param description // 书的描述（可选）
     */
    void add(String name, String description);
}