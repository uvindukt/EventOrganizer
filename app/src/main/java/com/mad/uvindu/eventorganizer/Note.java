package com.mad.uvindu.eventorganizer;

public class Note {

    private int noteId;
    private String noteTitle;
    private String noteContent;

    public Note() {}

    public Note(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                '}';
    }

}
