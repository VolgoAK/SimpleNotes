package com.volgoak.simplenotes.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.volgoak.simplenotes.App
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.model.NotesProvider
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by alex on 4/12/18.
 */
class NoteEditViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var provider: NotesProvider

    init {
        (application as App).getComponent().inject(this)
    }

    val noteLiveData : MutableLiveData<Note> = MutableLiveData()

    var note: Note? = null

    fun loadNote(id : Long) {
        note = provider.getNoteById(id)
        noteLiveData.value = note
    }

    fun saveNote(text : String, title : String) {
        Timber.d("save note $text")
        note?.text = text
        note?.name = title
        if(note != null) provider.insertNote(note!!)
    }
}