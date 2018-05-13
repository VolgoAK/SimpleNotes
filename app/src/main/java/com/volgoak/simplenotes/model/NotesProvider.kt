package com.volgoak.simplenotes.model

import android.arch.lifecycle.LiveData
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.NoteBook

/**
 * Created by alex on 4/12/18.
 */
interface NotesProvider {
    fun getAllNotes() : LiveData<List<Note>>

    fun getNoteById(id : Long) : Note

    fun getNotesByNotebookId(id: Long) : LiveData<List<Note>>

    fun insertNote(note : Note) : Long

    fun getAllNotebooks() : LiveData<List<NoteBook>>

    fun getNotebook(id : Long) : NoteBook

    fun insertNoteBook(noteBook: NoteBook) : Long

}