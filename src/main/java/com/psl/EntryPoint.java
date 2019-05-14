package com.psl;

public class EntryPoint {

    public static void main(String args[]) throws Exception {
        BookManager bookManager=new BookManager();
        bookManager.addBook(new Book("isbn1","title1","publisher1",1,false));
        bookManager.addBook(new Book("isbn2","title2","publisher2",2,false));
        System.out.println(bookManager.getMyBookMap());
        Book bookWithIsbnNull=new Book(null,"title1","pub1",1,false);
        bookManager.addBook(bookWithIsbnNull);
        System.out.println(bookManager.getMyBookMap().size());
        /*bookManager.lendedBook(new Book("isbn1","title1","publisher1",1));
        System.out.println(bookManager.getMyBookList());
        System.out.println(bookManager.getLendedBookList());*/
    }
}
