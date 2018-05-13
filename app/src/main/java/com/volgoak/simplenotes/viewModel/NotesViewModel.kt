package com.volgoak.simplenotes.viewModel

import android.app.Application
import android.arch.lifecycle.*
import com.volgoak.simplenotes.App
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.model.NotesProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by alex on 4/12/18.
 */
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var provider: NotesProvider

    private val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData()

    private val notesMediatorLiveData: MediatorLiveData<List<Note>> = MediatorLiveData()
    private var lastCallLiveData: LiveData<List<Note>>? = null

    private val notesObserver: Observer<List<Note>> = Observer { list ->
        Timber.d("All notes observed")
        notesLiveData.value = list
    }

    private var notebookId: Long = 0

    init {
        (application as App).getComponent().inject(this)
    }

    fun getNotes(id: Long): LiveData<List<Note>> {
        if (id != notebookId || notesLiveData.value == null) {

            //remove old sourse
            lastCallLiveData?.let {
                notesMediatorLiveData.removeSource(it)
            }

            notebookId = id
            lastCallLiveData = if (notebookId == 0L) {
                provider.getAllNotes()
            } else {
                provider.getNotesByNotebookId(id)
            }

            lastCallLiveData?.let {
                Timber.d("add source to mediator")
                notesMediatorLiveData.addSource(it, {
                   notesMediatorLiveData.value = it
                })
            }
        }

        return notesMediatorLiveData
    }

    fun createNewNote(): Long {
        val note = Note()
        note.notebook.targetId = notebookId

        return provider.insertNote(note)
    }

}