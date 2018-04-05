package test.dao.impl;


import dao.BookDao;
import dao.impl.BookDaoImpl;
import model.Book;

public class TestBookDaoImpl {
    private static BookDao bdi = new BookDaoImpl();

    public static Book[] find(String name) {
        Book[] books = bdi.findByName(name);
        for (Book book : books) {
            System.out.println(book.getID());
        }
        return books;
    }

    public static void main(String[] args) {
        find("Book1");
    }
}