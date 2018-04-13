package com.volgoak.simplenotes.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.volgoak.simplenotes.App
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.model.NotesProvider
import io.objectbox.query.Query
import javax.inject.Inject

/**
 * Created by alex on 4/12/18.
 */
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var provider: NotesProvider

    private val notesLiveData : MutableLiveData<List<Note>> = MutableLiveData()

    private var notebookId : Long = 0

    init {
        (application as App).getComponent().inject(this)
    }

    fun getNotes(id : Long) : LiveData<List<Note>> {
        if(id != notebookId || notesLiveData.value == null) {
            notebookId = id
            if(notebookId == 0L) {
                notesLiveData.value = provider.getAllNotes()
            } else {
                notesLiveData.value = provider.getNotesByNotebookId(id)
            }
        }

        return notesLiveData
    }

    fun createNewNote() : Long {
        val note = Note()
        note.notebook.targetId = notebookId

        return provider.insertNote(note)
    }

}