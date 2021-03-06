package io.github.martin1248.gtdlight2.database.internal;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String text;
    private int state;
    private int context;
    private int listOrder;

    @Ignore
    public NoteEntity() {

    }

    public NoteEntity(int id, Date date, String text, int state, int context, int listOrder) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.state = state;
        this.context = context;
        this.listOrder = listOrder;
    }

    @Ignore
    public NoteEntity(Date date, String text, int state, int context, int listOrder) {
        this.date = date;
        this.text = text;
        this.state = state;
        this.context = context;
        this.listOrder = listOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() { return state; }

    public void setState(int state) { this.state = state; }

    public int getContext() { return context; }

    public void setContext(int context) { this.context = context; }

    public int getListOrder() { return listOrder; }

    public void setListOrder(int listOrder) { this.listOrder = listOrder; }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", state=" + state +
                ", context=" + context +
                ", listOrder=" + listOrder +
                '}';
    }
}
