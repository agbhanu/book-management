package com.psl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class BookManagerTest{

    private BookManager bookManager;

    @Before
    public void setup(){
          bookManager=new BookManager();
    }

    @Test
    public void testAddBook_isbnOrTitleIsNull() {

        Book bookWithIsbnNull = new Book(null, "title1", "pub1", 1, false);
        bookManager.addBook(bookWithIsbnNull);
        assertTrue(bookManager.getMyBookMap().size() == 0);
    }

    @Test
    public void testAddBook_NotUniqueIsbn() {

        bookManager.addBook(getBook());
        bookManager.addBook(getBook());
        assertTrue(bookManager.getMyBookMap().size() == 1);
    }

    @Test
    public void testAddBook_MaxLimit(){

        bookManager.addBook(new Book("isbn1","title1","pub1",1,false));
        bookManager.addBook(new Book("isbn2","title1","pub1",1,false));
        bookManager.addBook(new Book("isbn3","title1","pub1",1,false));
        bookManager.addBook(new Book("isbn4","title1","pub1",1,false));
        bookManager.addBook(new Book("isbn5","title1","pub1",1,false));
        bookManager.addBook(new Book("isbn6","title1","pub1",1,false));
        assertTrue(bookManager.getMyBookMap().size()==5);
    }

    @Test
    public void testLendedBook_NotPresentInBookList() {

        bookManager.lendedBook("isbn1", "ankit");
        assertTrue(bookManager.getLendedBookMap().size() == 0);
    }

    @Test
    public void testLendedBook_AlreadyAssignedToSomeone() {
        Book assignedBook = new Book("isbn1", "title1", "pub1", 1, true);
        bookManager.addBook(assignedBook);
        bookManager.lendedBook("isbn1", "ankit");
        assertTrue(bookManager.getLendedBookMap().size() == 0);
    }

    @Test
    public void testLendedBook_MarkedAsAssigned() {
        Book unassignedBook = new Book("isbn2", "title2", "pub2", 1, false);
        bookManager.addBook(unassignedBook);
        bookManager.lendedBook("isbn2", "ankit");
        assertTrue(bookManager.getMyBookMap().get("isbn2").isAssignedOrNot() == true);
    }

    @Test
    public void testLendedBook_addedInFriendList(){
        Book unassignedBook = new Book("isbn2", "title2", "pub2", 1, false);
        bookManager.addBook(unassignedBook);
        bookManager.lendedBook("isbn2", "ankit");
        assertTrue(bookManager.getLendedBookMap().size()==1);
    }

    @Test
    public void getBackBook_NotPresentInFriendBookList() {

        Book unassignedBook = new Book("isbn2", "title2", "pub2", 1, false);
        bookManager.addBook(unassignedBook);
        bookManager.lendedBook("isbn2", "ankit");
        Book book = new Book("isbn1", "title1", "pub1", 1, true);
        bookManager.getBackBook(book, "ankit");
        assertTrue(bookManager.getLendedBookMap().size()==1);
    }

    @Test
    public void getBackBook_MarkedAsNotAssigned() {

        Book unassignedBook = new Book("isbn2", "title2", "pub2", 1, false);
        bookManager.addBook(unassignedBook);
        bookManager.lendedBook("isbn2", "ankit");
        unassignedBook.setAssignedOrNot(true);
        bookManager.getBackBook(bookManager.getMyBookMap().get("isbn2"), "ankit");
        assertTrue(bookManager.getMyBookMap().get("isbn2").isAssignedOrNot() == false);
    }

    @Test
    public void getBackBook_RemovedFromFriendBookList(){

        Book unassignedBook = new Book("isbn2", "title2", "pub2", 1, false);
        bookManager.addBook(unassignedBook);
        bookManager.lendedBook("isbn2", "ankit");
        unassignedBook.setAssignedOrNot(true);
        bookManager.getBackBook(bookManager.getMyBookMap().get("isbn2"), "ankit");
        assertTrue(bookManager.getLendedBookMap().size()==0);
    }

    public Book getBook(){
        return new Book("isbn1","title1","pub1",1,false);
    }
}
