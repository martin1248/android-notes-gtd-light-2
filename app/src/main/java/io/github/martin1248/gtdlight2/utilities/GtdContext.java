package io.github.martin1248.gtdlight2.utilities;

import java.util.ArrayList;

public enum GtdContext {
    IMPORTANT ("Important"),
    ERRANDS ("Errands"),
    MYGIRLFRIEND ("My girlfriend"),
    HOME ("Home"),
    COMPUTER ("Computer"),
    OFFICE ("Office"),
    NONE ("None");

    private final String name;

    private GtdContext(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }


    public static final ArrayList<GtdContext> contexts = new ArrayList<>();

    static {
        contexts.add(IMPORTANT);
        contexts.add(ERRANDS);
        contexts.add(MYGIRLFRIEND);
        contexts.add(HOME);
        contexts.add(COMPUTER);
        contexts.add(OFFICE);
        contexts.add(NONE);
    }

    public static ArrayList<String> getContextsAsStrings(){
        ArrayList<String> contextsAsString = new ArrayList<>();
        for (GtdContext context: contexts) {
            contextsAsString.add(context.toString());
        }
        return contextsAsString;
    }
}
