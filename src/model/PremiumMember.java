package model;

public class PremiumMember extends Member {
    public PremiumMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public int getBorrowLimit() {
        return 6;   // Premium members: max 6 books
    }

    // Additional method to show availability tracking (required extension)
    public String checkAvailability(Book book) {
        if (book.isAvailable()) {
            return "Book \"" + book.getTitle() + "\" is available now.";
        } else {
            return "Book \"" + book.getTitle() + "\" will be available on: " + book.getDueDate();
        }
    }
}