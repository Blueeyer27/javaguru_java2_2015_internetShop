package lv.javaguru.java2;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Anton on 2015.02.22..
 */
public enum AccessLevel {
    BANNED(0),
    GUEST(1),
    CLIENT(2),
    MODERATOR(3),
    ADMIN(4);

    private final int value;

    private AccessLevel(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}

