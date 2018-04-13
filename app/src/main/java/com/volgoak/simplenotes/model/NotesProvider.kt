package com.volgoak.simplenotes.model

import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.NoteBook

/**
 * Created by alex on 4/12/18.
 */
interface NotesProvider {
    fun getAllNotes() : List<Note>

    fun getNoteById(id : Long) : Note

    fun getNotesByNotebookId(id: Long) : List<Note>

    fun insertNote(note : Note) : Long

    fun getAllNotebooks() : List<NoteBook>

    fun insertNoteBook(noteBook: NoteBook) : Long

}