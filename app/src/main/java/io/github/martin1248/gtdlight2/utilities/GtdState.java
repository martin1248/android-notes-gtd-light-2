package io.github.martin1248.gtdlight2.utilities;

import java.util.ArrayList;

public enum GtdState {
    INBOX ("Inbox","#ffce00"),
    NEXT_ACTIONS ("Next actions","#006699"),
    CALENDER ("Calender","#b50346"),
    WAITING_FOR ("Waiting for","#eedfcc"),
    SOME_DAY ("Some day/Maybe","#5c3930"),
    REFERENCE ("Reference","#46c9b3"),
    TRASH ("Trash","#222222"),
    DONE ("Done","#279989");

    private final String name;
    private final String color;

    private GtdState(String s, String c) {
        name = s;
        color = c;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }



    public static final ArrayList<GtdState> states = new ArrayList<>();

    static {
        states.add(INBOX);
        states.add(NEXT_ACTIONS);
        states.add(CALENDER);
        states.add(WAITING_FOR);
        states.add(SOME_DAY);
        states.add(REFERENCE);
        states.add(TRASH);
        states.add(DONE);
    }

    public static ArrayList<String> getStatesAsStrings(){
        ArrayList<String> statesAsString = new ArrayList<>();
        for (GtdState state: states) {
            statesAsString.add(state.toString());
        }
        return statesAsString;
    }
}
