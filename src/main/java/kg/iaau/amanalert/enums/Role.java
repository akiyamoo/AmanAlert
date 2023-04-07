package kg.iaau.amanalert.enums;

public enum Role {
    ADMIN, WEB_USER, MOBILE_USER;

    public static String getSecured(Role role) {
        return role.name();
    }
}
