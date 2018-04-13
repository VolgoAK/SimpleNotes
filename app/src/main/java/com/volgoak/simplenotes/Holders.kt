package com.volgoak.simplenotes

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import timber.log.Timber

/**
 * Created by alex on 4/12/18.
 */

sealed class BaseHolder(view : View) : RecyclerView.ViewHolder(view) {
    var clickListener : ((BaseEntity) -> Unit)? = null
}

class NoteHolder(val view: View) : BaseHolder(view) {

    val nameTv : TextView = view.findViewById(R.id.tvNameHolder)
    val dateTv : TextView = view.findViewById(R.id.tvDateHolder)
    val shortTv : TextView = view.findViewById(R.id.tvShortTextHolder)

    fun bind(note: Note) {
        nameTv.text = note.name

        dateTv.text = Functions.timeToLocalDate(note.date, view.context)
        shortTv.text = Functions.htmlToSpannable(note.text)

        view.setOnClickListener{
            Timber.d("OnClickHolder")
            clickListener?.invoke(note)}
    }
}

class NotebookHolder(val view: View) : BaseHolder(view) {
    val noteBookNameTv : TextView = view.findViewById(R.id.tvNameNotebook)
    val notesCountTv : TextView = view.findViewById(R.id.tvNoteCountHolder)

    fun bind(noteBook: NoteBook) {
        noteBookNameTv.text = noteBook.name

        notesCountTv.text = "Notes ${noteBook.notes.size}"

        view.setOnClickListener{
            clickListener?.invoke(noteBook)
        }

    }
}