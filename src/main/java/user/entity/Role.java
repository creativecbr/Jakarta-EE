package user.entity;

public enum Role {
    ADMIN ("Administrator"),
    USER ("Normal user");

    private final String title;

    private Role(String s) {
        title = s;
    }

    public String toString() {
        return this.title;
    }
}
