package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public abstract class Member {
    protected String memberId;
    protected String name;
    protected String email;
    protected List<Book> borrowedBooks;
    protected List<String> borrowingHistory;  // store book titles or IDs

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.borrowingHistory = new ArrayList<>();
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }
    public List<String> getBorrowingHistory() { return borrowingHistory; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    // Abstract method - polymorphism
    public abstract int getBorrowLimit();

    // Borrow operation
    public boolean borrowBook(Book book, LocalDate dueDate) {
        if (borrowedBooks.size() >= getBorrowLimit()) {
            System.out.println("Cannot borrow. Limit of " + getBorrowLimit() + " books reached.");
            return false;
        }
        if (!book.isAvailable()) {
            System.out.println("Book is not available. It will be available on: " + book.getDueDate());
            return false;
        }
        borrowedBooks.add(book);
        borrowingHistory.add("Borrowed: " + book.getTitle() + " on " + LocalDate.now());
        book.borrowBook(this.memberId, dueDate);
        return true;
    }

    // Return operation
    public boolean returnBook(Book book) {
        if (!borrowedBooks.contains(book)) {
            System.out.println("You have not borrowed this book.");
            return false;
        }
        borrowedBooks.remove(book);
        borrowingHistory.add("Returned: " + book.getTitle() + " on " + LocalDate.now());
        book.returnBook();
        return true;
    }

    // Display borrowing history
    public void displayHistory() {
        System.out.println("Borrowing history for " + name + ":");
        if (borrowingHistory.isEmpty()) {
            System.out.println("  No history.");
        } else {
            for (String record : borrowingHistory) {
                System.out.println("  " + record);
            }
        }
    }
}