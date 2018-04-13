package com.volgoak.simplenotes.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.volgoak.simplenotes.*
import com.volgoak.simplenotes.model.NotesProvider
import javax.inject.Inject

/**
 * Created by alex on 4/12/18.
 */
class NotesActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val notesLiveData = MutableLiveData<List<Note>>()
    private val notebooksLiveData = MutableLiveData<List<NoteBook>>()
    val commandCallBack = SingleLiveEvent<Command>()

    @Inject
    lateinit var provider : NotesProvider

    init {
        (application as App).getComponent().inject(this)
    }

    fun getNotes() : LiveData<List<Note>> = notesLiveData

    fun getNotebooks() : LiveData<List<NoteBook>>{
        if(notebooksLiveData.value == null) {
            notebooksLiveData.value = provider.getAllNotebooks()
        }
        return notebooksLiveData
    }

    fun addFakeNotes() {
        val list = provider.getAllNotes()
        if(list.isEmpty()) {
            val defNoteBook = NoteBook()
            val notebookId = provider.insertNoteBook(defNoteBook)
            for(i in 0..5) {
                val note = Note("Test note $i", getApplication<App>().getString(R.string.dummy_text_long))
                provider.insertNote(note)
            }

            for(i in 0..7) {
                val note = Note("Test notebook note $i", "Text notebook text $i")
                note.notebook.targetId = notebookId
                provider.insertNote(note)
            }
        }

        notesLiveData.value = provider.getAllNotes()
    }

    fun openNote(id : Long) {
        commandCallBack.value = Command.OPEN_NOTE.withId(id)
    }

    fun openNoteBook(id : Long) {
        commandCallBack.value = Command.OPEN_NOTEBOOK.withId(id)
    }

    enum class Command {
        OPEN_NOTE,
        OPEN_NOTEBOOK;

        var id : Long = -2

        fun withId(id : Long) : Command {
            this.id = id
            return this
        }
    }
}