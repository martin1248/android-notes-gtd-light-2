package io.github.martin1248.gtdlight2.utilities;

import java.util.ArrayList;

public enum GtdState {
    INBOX ("Inbox"),
    NEXT_ACTIONS ("Next actions"),
    CALENDER ("Calender"),
    WAITING_FOR ("Waiting for"),
    SOME_DAY ("Some day"),
    REFERENCE ("Reference"),
    TRASH ("Trash"),
    DONE ("Done");

    private final String name;

    public static final ArrayList<String> allStates = new ArrayList<>();
    static {
        allStates.add(INBOX.toString());
        allStates.add(NEXT_ACTIONS.toString());
        allStates.add(CALENDER.toString());
        allStates.add(WAITING_FOR.toString());
        allStates.add(SOME_DAY.toString());
        allStates.add(REFERENCE.toString());
        allStates.add(TRASH.toString());
        allStates.add(DONE.toString());
    }
    public static int getStateAsPosition(GtdState state) {
        return GtdState.allStates.indexOf(state.toString());
    }

    private GtdState(String s) {
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
