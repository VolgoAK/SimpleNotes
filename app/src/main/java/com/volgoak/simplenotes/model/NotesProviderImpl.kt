package com.volgoak.simplenotes.model

import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.NoteBook
import com.volgoak.simplenotes.Note_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.QueryBuilder
import timber.log.Timber

/**
 * Created by alex on 4/12/18.
 */
class NotesProviderImpl(boxStore: BoxStore) : NotesProvider{
    private val noteBox = boxStore.boxFor(Note::class.java)
    private val notebookBox = boxStore.boxFor(NoteBook::class.java)

    override fun getAllNotes(): List<Note> {
        return noteBox.all
    }

    override fun getNoteById(id: Long): Note {
        return noteBox[id]
    }

    override fun getNotesByNotebookId(id: Long): List<Note> {
        val notesList = noteBox.query()
                .equal(Note_.notebookId, id)
                .order(Note_.date, QueryBuilder.DESCENDING)
                .build().find()

        Timber.d("Load notes by nb id - $id count ${notesList.size}")
        return notesList
    }

    override fun insertNote(note: Note) : Long{
        Timber.d("Insert note id ${note.id} text ${note.text}")
        return noteBox.put(note)
    }

    override fun getAllNotebooks(): List<NoteBook> {
        return notebookBox.all
    }

    override fun insertNoteBook(noteBook: NoteBook): Long {
        return notebookBox.put(noteBook)
    }
}