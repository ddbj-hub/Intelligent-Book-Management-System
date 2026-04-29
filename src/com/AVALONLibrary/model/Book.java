package com.AVALONLibrary.model;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean borrowed = false;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isBorrowed(){
        return this.borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "书名：" + title + " | " +
                "作者：" + author + " | " +
                "isbn：" + isbn + " | " +
                "状态：" + (this.borrowed ? "[已借出]" : "[可借]");
    }
}
