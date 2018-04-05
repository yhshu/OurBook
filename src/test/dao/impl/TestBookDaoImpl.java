package test.dao.impl;

import dao.impl.BookDaoImpl;
import model.Book;

public class TestBookDaoImpl{
    public static void main(String[] args){
        BookDaoImpl bdi = new BookDaoImpl();
        Book[] books = bdi.findByName("Book1");
        for(Book book : books){
            System.out.println(book.getID());
        }
    }
}