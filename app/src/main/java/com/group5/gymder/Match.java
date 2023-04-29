package com.group5.gymder;

public class Match {
    private User u1;

    private User u2;

    public Match(User u1, User u2) {
        this.u1 = u1;
        this.u2 = u2;
    }

    public User getU1() {
        return u1;
    }

    public User getU2() {
        return u2;
    }
}
