package com.volgoak.simplenotes.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.R
import com.volgoak.simplenotes.viewModel.NoteEditViewModel
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : AppCompatActivity() {

    lateinit var viewModel: NoteEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_note)

        setSupportActionBar(toolbarEditor)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        viewModel = ViewModelProviders.of(this).get(NoteEditViewModel::class.java)

        val noteId = intent.getLongExtra(EXTRA_NOTE_ID, 0)
        viewModel.loadNote(noteId)

        viewModel.noteLiveData.observe(this, Observer { note ->
            if (note != null) onNoteLoaded(note)
        })
    }

    private fun onNoteLoaded(note: Note) {
        richEditorNote.html = note.text
        etNoteNameEditor.setText(note.name)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveNote(richEditorNote.html, etNoteNameEditor.text.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_NOTE_ID = "extra_note_id"
    }
}
