package test.dao.impl;


import dao.BookDao;
import dao.impl.BookDaoImpl;
import model.Book;

public class TestBookDaoImpl {
    private static BookDao bdi = new BookDaoImpl();

    public static void addBooks() {
        for (int i = 1; i < 10; i++) {

        }
    }

    public static Book[] find(String name) {
        Book[] books = bdi.findByName("1");
        for (Book book : books) {
            System.out.println(book.getID());
        }
        return books;
    }

    public static void main(String[] args) {
        find("Book4");
    }
}