package io.github.martin1248.gtdlight2.database;

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

    @Ignore
    public NoteEntity() {

    }

    public NoteEntity(int id, Date date, String text, int state) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.state = state;
    }

    @Ignore
    public NoteEntity(Date date, String text, int state) {
        this.date = date;
        this.text = text;
        this.state = state;
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

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", state=" + state +
                '}';
    }
}
