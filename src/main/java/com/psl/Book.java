package com.psl;

public class Book {

    private String isbn;
    private String title;
    private String publisher;
    private int version;
    private boolean assignedOrNot;

    public Book() {}

    public Book(String isbn, String title, String publisher, int version, boolean assignedOrNot) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.version = version;
        this.assignedOrNot=assignedOrNot;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isAssignedOrNot() {
        return assignedOrNot;
    }

    public void setAssignedOrNot(boolean assignedOrNot) {
        this.assignedOrNot = assignedOrNot;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", version=" + version +
                ", assignedOrNot=" + assignedOrNot +
                '}';
    }
}
