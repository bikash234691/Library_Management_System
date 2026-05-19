package model;

import java.time.LocalDate;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private LocalDate dueDate;       // if borrowed, when it will be available
    private String borrowedBy;       // memberId of borrower

    public Book(String bookId, String title, String author, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
        this.dueDate = null;
        this.borrowedBy = null;
    }

    // Getters and Setters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }
    public LocalDate getDueDate() { return dueDate; }
    public String getBorrowedBy() { return borrowedBy; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setBorrowedBy(String borrowedBy) { this.borrowedBy = borrowedBy; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    // Helper to borrow the book
    public void borrowBook(String memberId, LocalDate dueDate) {
        this.available = false;
        this.borrowedBy = memberId;
        this.dueDate = dueDate;
    }

    // Helper to return the book
    public void returnBook() {
        this.available = true;
        this.borrowedBy = null;
        this.dueDate = null;
    }

    // Display info
    public void displayInfo() {
        System.out.println("ID: " + bookId + " | Title: " + title + " | Author: " + author +
                " | ISBN: " + isbn + " | Available: " + (available ? "Yes" : "No") +
                (dueDate != null ? " | Due: " + dueDate : ""));
    }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")";
    }
}