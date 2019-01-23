package io.github.martin1248.gtdlight2.utilities;

public enum GtdStates {
    INBOX ("Inbox"),
    NEXT_ACTIONS ("Next actions"),
    CALENDER ("Calender"),
    WAITING_FOR ("Waiting for"),
    SOME_DAY ("Some day"),
    REFERENCE ("Reference"),
    TRASH ("Trash"),
    DONE ("Done");

    private final String name;

    private GtdStates(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
