package io.github.martin1248.gtdlight2.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.github.martin1248.gtdlight2.database.internal.NoteEntity;

public class SampleData {

    private static final String SAMPLE_TEXT_1 = "A simple note";
    private static final String SAMPLE_TEXT_2 = "A note with a\nline feed";
    private static final String SAMPLE_TEXT_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<NoteEntity> getNotes() {
        List<NoteEntity> notes = new ArrayList<>();
        notes.add(new NoteEntity(getDate(0), SAMPLE_TEXT_1, 0,0));
        notes.add(new NoteEntity(getDate(-1), SAMPLE_TEXT_2, 0,0));
        notes.add(new NoteEntity(getDate(-2), SAMPLE_TEXT_3, 0,0));
        notes.add(new NoteEntity(getDate(-3), "Sample note: Inbox", 0,0));
        notes.add(new NoteEntity(getDate(-4), "Sample note: Next Action1", 1,0));
        notes.add(new NoteEntity(getDate(-5), "Sample note: Next Action2", 1,0));
        notes.add(new NoteEntity(getDate(-6), "Sample note: Next Action3", 1,1));
        notes.add(new NoteEntity(getDate(-7), "Sample note: Next Action4", 1,2));
        notes.add(new NoteEntity(getDate(-8), "Sample note: Next Action5", 1,3));
        notes.add(new NoteEntity(getDate(-9), "Sample note: Next Action6", 1,4));
        notes.add(new NoteEntity(getDate(-10), "Sample note: Next Action7", 1,0));
        notes.add(new NoteEntity(getDate(-11), "Sample note: Next Action8", 1,0));
        notes.add(new NoteEntity(getDate(-12), "Sample note: Calender", 2,0));
        return notes;
    }
}
