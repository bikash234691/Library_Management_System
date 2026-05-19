package service;

import model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library implements Searchable {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;
    private int nextLoanId = 1;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        loans = new ArrayList<>();
    }

    // ----- Book Management -----
    public void addBook(Book book) {
        if (findBookById(book.getBookId()) != null) {
            System.out.println("Book ID already exists.");
            return;
        }
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void updateBook(String bookId, String newTitle, String newAuthor, String newIsbn) {
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (newTitle != null && !newTitle.trim().isEmpty()) book.setTitle(newTitle);
        if (newAuthor != null && !newAuthor.trim().isEmpty()) book.setAuthor(newAuthor);
        if (newIsbn != null && InputValidator.isValidISBN(newIsbn)) book.setIsbn(newIsbn);
        System.out.println("Book updated.");
    }

    public void removeBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Cannot remove a borrowed book.");
            return;
        }
        books.remove(book);
        System.out.println("Book removed.");
    }

    // ----- Member Management -----
    public void registerMember(Member member) {
        if (findMemberById(member.getMemberId()) != null) {
            System.out.println("Member ID already exists.");
            return;
        }
        members.add(member);
        System.out.println("Member registered: " + member.getName());
    }

    public void updateMemberInfo(String memberId, String newName, String newEmail) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        if (newName != null && !newName.trim().isEmpty()) member.setName(newName);
        if (newEmail != null && InputValidator.isValidEmail(newEmail)) member.setEmail(newEmail);
        System.out.println("Member info updated.");
    }

    public void viewMemberHistory(String memberId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        member.displayHistory();
    }

    // ----- Borrowing Operations -----
    public void borrowBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailable()) {
            // Availability tracking: show when available
            System.out.println("Book is not available. It will be available on: " + book.getDueDate());
            return;
        }

        LocalDate dueDate = LocalDate.now().plusDays(14);
        boolean success = member.borrowBook(book, dueDate);
        if (success) {
            Loan loan = new Loan("L" + nextLoanId++, book, member, LocalDate.now(), dueDate);
            loans.add(loan);
            System.out.println("Book borrowed successfully. Due date: " + dueDate);
        }
    }

    public void returnBook(String memberId, String bookId) {
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        if (member == null || book == null) {
            System.out.println("Member or Book not found.");
            return;
        }
        if (!member.getBorrowedBooks().contains(book)) {
            System.out.println("This member has not borrowed this book.");
            return;
        }

        boolean success = member.returnBook(book);
        if (success) {
            // Mark loan as returned
            for (Loan loan : loans) {
                if (loan.getBook().equals(book) && loan.getMember().equals(member) && !loan.isReturned()) {
                    loan.setReturned(true);
                    break;
                }
            }
            System.out.println("Book returned successfully.");
        }
    }

    // ----- Search methods (interface implementation) -----
    @Override
    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByISBN(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    // ----- Availability Tracking (required for Group 6) -----
    public void trackAvailability(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (book.isAvailable()) {
            System.out.println("Book \"" + book.getTitle() + "\" is available now.");
        } else {
            System.out.println("Book \"" + book.getTitle() + "\" will be available on: " + book.getDueDate());
        }
    }

    // ----- Utility methods -----
    private Book findBookById(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) return b;
        }
        return null;
    }

    private Member findMemberById(String memberId) {
        for (Member m : members) {
            if (m.getMemberId().equals(memberId)) return m;
        }
        return null;
    }

    public void displayAllBooks() {
        if (books.isEmpty()) System.out.println("No books in library.");
        else books.forEach(Book::displayInfo);
    }

    public void displayAllMembers() {
        if (members.isEmpty()) System.out.println("No members registered.");
        else members.forEach(m -> System.out.println(m.getMemberId() + " | " + m.getName() + " | " + m.getEmail()));
    }

    public void displayCurrentLoans() {
        if (loans.isEmpty()) System.out.println("No active loans.");
        else loans.forEach(Loan::displayLoanDetails);
    }
}