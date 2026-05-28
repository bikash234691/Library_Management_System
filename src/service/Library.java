package service;

import model.Book;
import model.Loan;
import model.Member;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library implements Searchable {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    private int nextLoanId = 1;

    // ===================================================
    // ADD BOOK
    // ===================================================

    public void addBook(Book book) {

        if (findBookById(book.getBookId()) != null) {
            System.out.println("Book ID already exists.");
            return;
        }

        books.add(book);

        System.out.println("Book added: " + book.getTitle());
    }

    // ===================================================
    // UPDATE BOOK
    // ===================================================

    public void updateBook(String bookId,
                           String title,
                           String author,
                           String isbn) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (title != null && !title.trim().isEmpty()) {
            book.setTitle(title);
        }

        if (author != null && !author.trim().isEmpty()) {
            book.setAuthor(author);
        }

        if (isbn != null && InputValidator.isValidISBN(isbn)) {
            book.setIsbn(isbn);
        }

        System.out.println("Book updated.");
    }

    // ===================================================
    // REMOVE BOOK
    // ===================================================

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

    // ===================================================
    // REGISTER MEMBER
    // ===================================================

    public void registerMember(Member member) {

        if (findMemberById(member.getMemberId()) != null) {
            System.out.println("Member ID already exists.");
            return;
        }

        members.add(member);

        System.out.println("Member registered: " + member.getName());
    }

    // ===================================================
    // UPDATE MEMBER INFO
    // ===================================================

    public void updateMemberInfo(String memberId,
                                 String newName,
                                 String newEmail) {

        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            member.setName(newName);
        }

        if (newEmail != null && InputValidator.isValidEmail(newEmail)) {
            member.setEmail(newEmail);
        }

        System.out.println("Member info updated.");
    }

    // ===================================================
    // VIEW MEMBER HISTORY
    // ===================================================

    public void viewMemberHistory(String memberId) {

        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        member.displayHistory();
    }

    // ===================================================
    // BORROW BOOK
    // ===================================================

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

            System.out.println(
                    "Book is not available. It will be available on: "
                            + book.getDueDate()
            );

            return;
        }

        LocalDate dueDate = LocalDate.now().plusDays(14);

        boolean borrowed = member.borrowBook(book, dueDate);

        if (borrowed) {

            Loan loan = new Loan(
                    "L" + nextLoanId++,
                    book,
                    member,
                    LocalDate.now(),
                    dueDate
            );

            loans.add(loan);

            System.out.println(
                    "Book borrowed successfully. Due date: "
                            + dueDate
            );
        }
    }

    // ===================================================
    // RETURN BOOK
    // ===================================================

    public void returnBook(String memberId, String bookId) {

        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        if (member == null || book == null) {
            System.out.println("Member or Book not found.");
            return;
        }

        if (!member.getBorrowedBooks().contains(book)) {
            System.out.println(
                    "This member has not borrowed this book."
            );
            return;
        }

        boolean returned = member.returnBook(book);

        if (returned) {

            // Mark loan as returned

            for (Loan loan : loans) {

                if (
                        loan.getBook().equals(book)
                        && loan.getMember().equals(member)
                        && !loan.isReturned()
                ) {

                    loan.setReturned(true);
                    break;
                }
            }

            System.out.println("Book returned successfully.");
        }
    }

    // ===================================================
    // SEARCH METHODS
    // ===================================================

    public List<Book> searchByTitle(String keyword) {

        return books.stream()
                .filter(book ->
                        book.getTitle()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String keyword) {

        return books.stream()
                .filter(book ->
                        book.getAuthor()
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByISBN(String isbn) {

        return books.stream()
                .filter(book ->
                        book.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    // ===================================================
    // TRACK AVAILABILITY
    // ===================================================

    public void trackAvailability(String bookId) {

        Book book = findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isAvailable()) {

            System.out.println(
                    "Book \"" + book.getTitle()
                            + "\" is available now."
            );

        } else {

            System.out.println(
                    "Book \"" + book.getTitle()
                            + "\" will be available on: "
                            + book.getDueDate()
            );
        }
    }

    // ===================================================
    // FIND BOOK BY ID
    // ===================================================

    private Book findBookById(String bookId) {

        for (Book book : books) {

            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }

        return null;
    }

    // ===================================================
    // FIND MEMBER BY ID
    // ===================================================

    private Member findMemberById(String memberId) {

        for (Member member : members) {

            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }

        return null;
    }

    // ===================================================
    // DISPLAY ALL BOOKS
    // ===================================================

    public void displayAllBooks() {

        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        books.forEach(Book::displayInfo);
    }

    // ===================================================
    // DISPLAY ALL MEMBERS
    // ===================================================

    public void displayAllMembers() {

        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        members.forEach(member ->

                System.out.println(
                        member.getMemberId()
                                + " | "
                                + member.getName()
                                + " | "
                                + member.getEmail()
                )
        );
    }

    // ===================================================
    // DISPLAY CURRENT ACTIVE LOANS
    // ===================================================

    public void displayCurrentLoans(String memberId) {

        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        boolean found = false;

        System.out.println("\n===== CURRENT ACTIVE LOANS =====");

        for (Loan loan : loans) {

            // Show only active loans for this member

            if (
                    loan.getMember()
                            .getMemberId()
                            .equals(memberId)

                            &&

                    !loan.isReturned()
            ) {

                loan.displayLoanDetails();
                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No active loans found for member ID: "
                            + memberId
            );
        }
    }
}

