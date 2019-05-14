package com.psl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookManager {

    private Map<String,Book> myBookMap;
    private Map<String,List<Book>> lendedBookMap;

    public BookManager(){
        myBookMap =new HashMap<>();
        lendedBookMap =new HashMap<>();
    }

    public Map<String, Book> getMyBookMap() {
        return myBookMap;
    }

    public void setMyBookMap(Map<String, Book> myBookMap) {
        this.myBookMap = myBookMap;
    }

    public Map<String, List<Book>> getLendedBookMap() {
        return lendedBookMap;
    }

    public void setLendedBookMap(Map<String, List<Book>> lendedBookMap) {
        this.lendedBookMap = lendedBookMap;
    }

    @Override
    public String toString() {
        return "BookList{" +
                "myBookList=" + myBookMap +
                ", lendedBookList=" + lendedBookMap +
                '}';
    }

    public void addBook(Book book){

        try {
            // isbn and title must be present for a book
            if (book.getIsbn() == null || book.getTitle() == null) {
                throw new Exception("Isbn or Title can not be null");
            }


        // isbn should be unique for each book
        if(myBookMap.containsKey(book.getIsbn())) {
            throw new Exception("Book is already added in book list");
        }

        // book list can contain maximum 5 books
        if(myBookMap.size()>=5){
            throw new Exception("Can not added more books");
        }

        // added book in book list
        myBookMap.put(book.getIsbn(),book);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void lendedBook(String isbn,String friendName){

        try {
            // book should be present in book list
            if (!myBookMap.containsKey(isbn))
                throw new Exception("Book is not present in list");

            // get particular book
            Book book = myBookMap.get(isbn);

            // book must not be assigned to someone else earlier
            if (book.isAssignedOrNot() == true)
                throw new Exception("Book is already assigned to someone else");

            // Book must be marked as assigned
            book.setAssignedOrNot(true);
            if (myBookMap.containsKey(book.getIsbn())) {
                myBookMap.put(book.getIsbn(), book);
            }

            // Check if some other books are already assigned to same friend
            if (lendedBookMap.containsKey(friendName)) {
                List<Book> bookList = lendedBookMap.get(friendName);
                bookList.add(book);
                lendedBookMap.put(friendName, bookList);
            }

            // First time book assigned to friend
            else {
                List<Book> bookList = new ArrayList<>();
                bookList.add(book);
                lendedBookMap.put(friendName, bookList);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getBackBook(Book book, String friendName){

        try {
            // Friend does not contain any book
            if (!lendedBookMap.containsKey(friendName)) {
                throw new Exception("There is no book assigned to " + friendName+" with title "+book.getTitle());
            }

            // Friend contain some books but not a particular book
            if (!lendedBookMap.get(friendName).contains(book)) {
                throw new Exception("Book with isbn " + book.getTitle() + " is not present");
            }

            // Remove particular book from friend's book list
            List<Book> bookList = lendedBookMap.get(friendName);
            bookList.remove(book);

            if (bookList.size() == 0)
                lendedBookMap.remove(friendName);
            else
                lendedBookMap.put(friendName, bookList);

            book.setAssignedOrNot(false);
            if (myBookMap.containsKey(book.getIsbn())) {
                myBookMap.put(book.getIsbn(), book);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}