
package model;

import java.time.LocalDate;

public class Loan {

    private String loanId;
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    public Loan(String loanId,
                Book book,
                Member member,
                LocalDate borrowDate,
                LocalDate dueDate) {

        this.loanId = loanId;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    // ===================================================
    // GETTERS
    // ===================================================

    public String getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    // ===================================================
    // SETTERS
    // ===================================================

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // ===================================================
    // DISPLAY LOAN DETAILS
    // ===================================================

    public void displayLoanDetails() {

        String status;

        if (returned) {
            status = "Returned";
        }
        else if (LocalDate.now().isAfter(dueDate)) {
            status = "OVERDUE";
        }
        else {
            status = "Active";
        }

        System.out.println(
                "Loan ID: " + loanId +
                " | Member ID: " + member.getMemberId() +
                " | Member: " + member.getName() +
                " | Book: " + book.getTitle() +
                " | Borrow Date: " + borrowDate +
                " | Due Date: " + dueDate +
                " | Status: " + status
        );
    }
}
