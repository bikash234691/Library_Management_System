package model;

public class StandardMember extends Member {
    public StandardMember(String memberId, String name, String email) {
        super(memberId, name, email);
    }

    @Override
    public int getBorrowLimit() {
        return 3;   // Standard members: max 3 books
    }
}