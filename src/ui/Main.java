package ui;

import model.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("===== Library Management System =====");

        while (true) {

            displayMainMenu();

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    bookManagementMenu();
                    break;

                case "2":
                    memberManagementMenu();
                    break;

                case "3":
                    borrowBookUI();
                    break;

                case "4":
                    returnBookUI();
                    break;

                case "5":
                    searchBookMenu();
                    break;

                case "6":
                    viewCurrentLoansUI();
                    break;

                case "7":
                    trackAvailabilityUI();
                    break;

                case "8":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ===================================================
    // MAIN MENU
    // ===================================================

    private static void displayMainMenu() {

        System.out.println("\n===== MAIN MENU =====");

        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book");
        System.out.println("6. View Active Loans");
        System.out.println("7. Track Book Availability");
        System.out.println("8. Exit");

        System.out.print("Choice: ");
    }

    // ===================================================
    // BOOK MANAGEMENT MENU
    // ===================================================

    private static void bookManagementMenu() {

        System.out.println("\n--- Book Management ---");

        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. List All Books");
        System.out.println("5. Back");

        System.out.print("Choice: ");

        String opt = scanner.nextLine();

        switch (opt) {

            case "1":
                addBookUI();
                break;

            case "2":
                updateBookUI();
                break;

            case "3":
                removeBookUI();
                break;

            case "4":
                library.displayAllBooks();
                break;

            case "5":
                return;

            default:
                System.out.println("Invalid.");
        }
    }

    // ===================================================
    // ADD BOOK
    // ===================================================

    private static void addBookUI() {

        System.out.print("Book ID: ");
        String id = scanner.nextLine();

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("ISBN (10 or 13 digits): ");
        String isbn = scanner.nextLine();

        if (!InputValidator.isValidISBN(isbn)) {

            System.out.println("Invalid ISBN format.");
            return;
        }

        library.addBook(
                new Book(id, title, author, isbn)
        );
    }

    // ===================================================
    // UPDATE BOOK
    // ===================================================

    private static void updateBookUI() {

        System.out.print("Book ID to update: ");
        String id = scanner.nextLine();

        System.out.print("New Title (or press enter to skip): ");
        String title = scanner.nextLine();

        System.out.print("New Author (or press enter to skip): ");
        String author = scanner.nextLine();

        System.out.print("New ISBN (or press enter to skip): ");
        String isbn = scanner.nextLine();

        library.updateBook(
                id,
                title.isEmpty() ? null : title,
                author.isEmpty() ? null : author,
                isbn.isEmpty() ? null : isbn
        );
    }

    // ===================================================
    // REMOVE BOOK
    // ===================================================

    private static void removeBookUI() {

        System.out.print("Book ID to remove: ");

        String id = scanner.nextLine();

        library.removeBook(id);
    }

    // ===================================================
    // MEMBER MANAGEMENT MENU
    // ===================================================

    private static void memberManagementMenu() {

        System.out.println("\n--- Member Management ---");

        System.out.println("1. Register Member");
        System.out.println("2. Update Member Info");
        System.out.println("3. View Borrowing History");
        System.out.println("4. List All Members");
        System.out.println("5. Back");

        System.out.print("Choice: ");

        String opt = scanner.nextLine();

        switch (opt) {

            case "1":
                registerMemberUI();
                break;

            case "2":
                updateMemberUI();
                break;

            case "3":
                viewHistoryUI();
                break;

            case "4":
                library.displayAllMembers();
                break;

            case "5":
                return;

            default:
                System.out.println("Invalid.");
        }
    }

    // ===================================================
    // REGISTER MEMBER
    // ===================================================

    private static void registerMemberUI() {

        System.out.print("Member ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (!InputValidator.isValidEmail(email)) {

            System.out.println("Invalid email format.");
            return;
        }

        System.out.print(
                "Membership Type (1-Standard / 2-Premium): "
        );

        String type = scanner.nextLine();

        Member member;

        if ("2".equals(type)) {

            member = new PremiumMember(id, name, email);

            System.out.println(
                    "Premium member registered (6-book limit)."
            );

        } else {

            member = new StandardMember(id, name, email);

            System.out.println(
                    "Standard member registered (3-book limit)."
            );
        }

        library.registerMember(member);
    }

    // ===================================================
    // UPDATE MEMBER
    // ===================================================

    private static void updateMemberUI() {

        System.out.print("Member ID: ");
        String id = scanner.nextLine();

        System.out.print(
                "New Name (or press enter to skip): "
        );

        String name = scanner.nextLine();

        System.out.print(
                "New Email (or press enter to skip): "
        );

        String email = scanner.nextLine();

        library.updateMemberInfo(
                id,
                name.isEmpty() ? null : name,
                email.isEmpty() ? null : email
        );
    }

    // ===================================================
    // VIEW BORROW HISTORY
    // ===================================================

    private static void viewHistoryUI() {

        System.out.print("Member ID: ");

        String id = scanner.nextLine();

        library.viewMemberHistory(id);
    }

    // ===================================================
    // BORROW BOOK
    // ===================================================

    private static void borrowBookUI() {

        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();

        System.out.print("Book ID: ");
        String bookId = scanner.nextLine();

        library.borrowBook(memberId, bookId);
    }

    // ===================================================
    // RETURN BOOK
    // ===================================================

    private static void returnBookUI() {

        System.out.print("Member ID: ");
        String memberId = scanner.nextLine();

        System.out.print("Book ID: ");
        String bookId = scanner.nextLine();

        library.returnBook(memberId, bookId);
    }

    // ===================================================
    // VIEW ACTIVE LOANS
    // ===================================================

    private static void viewCurrentLoansUI() {

        System.out.print("Enter Member ID: ");

        String memberId = scanner.nextLine();

        library.displayCurrentLoans(memberId);
    }

    // ===================================================
    // SEARCH BOOK MENU
    // ===================================================

    private static void searchBookMenu() {

        System.out.println("\n--- Search Book ---");

        System.out.println("1. By Title");
        System.out.println("2. By Author");
        System.out.println("3. By ISBN");

        System.out.print("Choice: ");

        String opt = scanner.nextLine();

        System.out.print("Enter search term: ");

        String term = scanner.nextLine();

        List<Book> results;

        switch (opt) {

            case "1":
                results = library.searchByTitle(term);
                break;

            case "2":
                results = library.searchByAuthor(term);
                break;

            case "3":
                results = library.searchByISBN(term);
                break;

            default:
                System.out.println("Invalid.");
                return;
        }

        if (results.isEmpty()) {

            System.out.println("No books found.");

        } else {

            results.forEach(Book::displayInfo);
        }
    }

    // ===================================================
    // TRACK AVAILABILITY
    // ===================================================

    private static void trackAvailabilityUI() {

        System.out.print("Enter Book ID: ");

        String id = scanner.nextLine();

        library.trackAvailability(id);
    }
}

