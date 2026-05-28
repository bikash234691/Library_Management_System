
// LibrarySystemTest.java
// Professional-style manual test suite (without JUnit)

import model.Book;
import model.Member;
import model.PremiumMember;

import java.time.LocalDate;

// Concrete class for testing standard members
class StandardMember extends Member {

    public StandardMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public int getBorrowLimit() {
        return 3;
    }
}



public class LibrarySystemTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("===== LIBRARY MANAGEMENT SYSTEM TESTS =====\n");

        testStandardMemberBorrowLimit();
        testPremiumMemberBorrowLimit();
        testBorrowBookSuccess();
        testBorrowUnavailableBook();
        testReturnBookSuccess();
        testReturnNonBorrowedBook();
        testStandardBorrowLimitRestriction();
        testPremiumBorrowLimitRestriction();
        testAvailabilityTracking();

        System.out.println("\n==================================");
        System.out.println("PASSED: " + passed);
        System.out.println("FAILED: " + failed);

        if (failed == 0) {
            System.out.println("✅ ALL TESTS PASSED");
        } else {
            System.out.println("❌ SOME TESTS FAILED");
        }
    }

    // ===================================================
    // ASSERTION HELPER
    // ===================================================

    private static void assertTest(boolean condition, String testName) {

        if (condition) {
            passed++;
            System.out.println("✓ " + testName);
        } else {
            failed++;
            System.out.println("✗ " + testName);
        }
    }

    // ===================================================
    // TEST STANDARD MEMBER LIMIT
    // ===================================================

    private static void testStandardMemberBorrowLimit() {

        Member member = new StandardMember(
                "M001",
                "John",
                "john@email.com"
        );

        assertTest(
                member.getBorrowLimit() == 3,
                "Standard member borrow limit should be 3"
        );
    }

    // ===================================================
    // TEST PREMIUM MEMBER LIMIT
    // ===================================================

    private static void testPremiumMemberBorrowLimit() {

        Member member = new PremiumMember(
                "P001",
                "Alice",
                "alice@email.com"
        );

        assertTest(
                member.getBorrowLimit() == 6,
                "Premium member borrow limit should be 6"
        );
    }

    // ===================================================
    // TEST SUCCESSFUL BORROW
    // ===================================================

    private static void testBorrowBookSuccess() {

        Member member = new StandardMember(
                "M002",
                "Bob",
                "bob@email.com"
        );

        Book book = new Book(
                "B001",
                "Java Basics",
                "Smith",
                "111"
        );

        boolean result = member.borrowBook(
                book,
                LocalDate.now().plusDays(14)
        );

        boolean conditions =
                result
                && !book.isAvailable()
                && book.getBorrowedBy().equals("M002")
                && member.getBorrowedBooks().contains(book);

        assertTest(
                conditions,
                "Borrowing available book should succeed"
        );
    }

    // ===================================================
    // TEST BORROWING UNAVAILABLE BOOK
    // ===================================================

    private static void testBorrowUnavailableBook() {

        Member member1 = new StandardMember(
                "M003",
                "Tom",
                "tom@email.com"
        );

        Member member2 = new StandardMember(
                "M004",
                "Jerry",
                "jerry@email.com"
        );

        Book book = new Book(
                "B002",
                "OOP Design",
                "Brown",
                "222"
        );

        member1.borrowBook(
                book,
                LocalDate.now().plusDays(14)
        );

        boolean result = member2.borrowBook(
                book,
                LocalDate.now().plusDays(14)
        );

        assertTest(
                !result,
                "Borrowing unavailable book should fail"
        );
    }

    // ===================================================
    // TEST RETURN BOOK SUCCESS
    // ===================================================

    private static void testReturnBookSuccess() {

        Member member = new StandardMember(
                "M005",
                "Sarah",
                "sarah@email.com"
        );

        Book book = new Book(
                "B003",
                "Algorithms",
                "White",
                "333"
        );

        member.borrowBook(
                book,
                LocalDate.now().plusDays(14)
        );

        boolean result = member.returnBook(book);

        boolean conditions =
                result
                && book.isAvailable()
                && book.getBorrowedBy() == null
                && !member.getBorrowedBooks().contains(book);

        assertTest(
                conditions,
                "Returning borrowed book should succeed"
        );
    }

    // ===================================================
    // TEST RETURN NON-BORROWED BOOK
    // ===================================================

    private static void testReturnNonBorrowedBook() {

        Member member = new StandardMember(
                "M006",
                "David",
                "david@email.com"
        );

        Book book = new Book(
                "B004",
                "Networks",
                "Green",
                "444"
        );

        boolean result = member.returnBook(book);

        assertTest(
                !result,
                "Returning non-borrowed book should fail"
        );
    }

    // ===================================================
    // TEST STANDARD MEMBER LIMIT RESTRICTION
    // ===================================================

    private static void testStandardBorrowLimitRestriction() {

        Member member = new StandardMember(
                "M007",
                "Chris",
                "chris@email.com"
        );

        // Borrow 3 books
        for (int i = 1; i <= 3; i++) {

            Book book = new Book(
                    "S" + i,
                    "Book" + i,
                    "Author",
                    "ISBN" + i
            );

            member.borrowBook(
                    book,
                    LocalDate.now().plusDays(14)
            );
        }

        // Attempt 4th book
        Book extraBook = new Book(
                "S4",
                "Extra Book",
                "Author",
                "ISBN4"
        );

        boolean result = member.borrowBook(
                extraBook,
                LocalDate.now().plusDays(14)
        );

        assertTest(
                !result,
                "Standard member should not borrow more than 3 books"
        );
    }

    // ===================================================
    // TEST PREMIUM MEMBER LIMIT RESTRICTION
    // ===================================================

    private static void testPremiumBorrowLimitRestriction() {

        Member member = new PremiumMember(
                "P002",
                "Premium User",
                "premium@email.com"
        );

        // Borrow 6 books
        for (int i = 1; i <= 6; i++) {

            Book book = new Book(
                    "P" + i,
                    "PremiumBook" + i,
                    "Author",
                    "ISBN" + i
            );

            member.borrowBook(
                    book,
                    LocalDate.now().plusDays(14)
            );
        }

        // Attempt 7th book
        Book extraBook = new Book(
                "P7",
                "Extra Premium Book",
                "Author",
                "ISBN7"
        );

        boolean result = member.borrowBook(
                extraBook,
                LocalDate.now().plusDays(14)
        );

        assertTest(
                !result,
                "Premium member should not borrow more than 6 books"
        );
    }

    // ===================================================
    // TEST AVAILABILITY TRACKING
    // ===================================================

    /**
 * 
 */
private static void testAvailabilityTracking() {

        PremiumMember member = new PremiumMember(
                "P003",
                "Lisa",
                "lisa@email.com"
        );

        Book book = new Book(
                "B005",
                "Database Systems",
                "Taylor",
                "555"
        );

        LocalDate dueDate = LocalDate.now().plusDays(14);

        member.borrowBook(book, dueDate);

        String message = member.checkAvailability(book);

        boolean conditions =
                message.contains("available on")
                && message.contains(dueDate.toString());

        assertTest(
                conditions,
                "Availability tracking should display due date"
        );
    }
}