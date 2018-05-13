package com.volgoak.simplenotes.model

import android.arch.lifecycle.LiveData
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.NoteBook
import com.volgoak.simplenotes.NoteBook_
import com.volgoak.simplenotes.Note_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.QueryBuilder
import timber.log.Timber

/**
 * Created by alex on 4/12/18.
 */
class NotesProviderImpl(boxStore: BoxStore) : NotesProvider{
    private val noteBox = boxStore.boxFor(Note::class.java)
    private val notebookBox = boxStore.boxFor(NoteBook::class.java)

    private var allNotesLiveData : LiveData<List<Note>>? = null
    private var allNotebooksLiveData : LiveData<List<NoteBook>>? = null

    override fun getAllNotes(): LiveData<List<Note>> {
        if(allNotesLiveData == null) {
            allNotesLiveData = ObjectBoxLiveData(
                    noteBox.query().order(Note_.date, QueryBuilder.DESCENDING).build())
        }

        return allNotesLiveData!!
    }

    override fun getNoteById(id: Long): Note {
        return noteBox[id]
    }

    override fun getNotesByNotebookId(id: Long): LiveData<List<Note>> {
        return ObjectBoxLiveData(noteBox.query()
                .equal(Note_.notebookId, id)
                .order(Note_.date, QueryBuilder.DESCENDING)
                .build())
    }

    override fun insertNote(note: Note) : Long{
        Timber.d("Insert note id ${note.id} text ${note.text}")
        return noteBox.put(note)
    }

    override fun getAllNotebooks(): LiveData<List<NoteBook>> {
        if(allNotebooksLiveData == null) {
            allNotebooksLiveData = ObjectBoxLiveData(
                    notebookBox.query()
                            .order(NoteBook_.name)
                            .build())
        }

        return allNotebooksLiveData!!
    }

    override fun getNotebook(id: Long): NoteBook {
        return notebookBox.get(id)
    }

    override fun insertNoteBook(noteBook: NoteBook): Long {
        return notebookBox.put(noteBook)
    }
}