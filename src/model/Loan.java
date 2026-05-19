package model;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    public Loan(String loanId, Book book, Member member, LocalDate borrowDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    // Getters
    public String getLoanId() { return loanId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isReturned() { return returned; }

    public void setReturned(boolean returned) { this.returned = returned; }

    public void displayLoanDetails() {
        System.out.println("Loan ID: " + loanId + " | Book: " + book.getTitle() +
                " | Member: " + member.getName() + " | Due: " + dueDate +
                " | Status: " + (returned ? "Returned" : (LocalDate.now().isAfter(dueDate) ? "OVERDUE" : "Active")));
    }
}