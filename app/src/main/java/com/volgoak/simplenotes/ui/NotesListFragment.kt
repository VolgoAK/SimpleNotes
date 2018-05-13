package com.volgoak.simplenotes.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volgoak.simplenotes.BaseEntity
import com.volgoak.simplenotes.Note
import com.volgoak.simplenotes.R
import com.volgoak.simplenotes.RvAdapter
import com.volgoak.simplenotes.viewModel.NotesActivityViewModel
import com.volgoak.simplenotes.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_notes.*


/**
 * A simple [Fragment] subclass.
 */
class NotesListFragment : Fragment() {

    lateinit var activityViewModel: NotesActivityViewModel
    lateinit var notesViewModel : NotesViewModel

    var notebookId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_notes, container, false)

        activityViewModel = ViewModelProviders.of(activity!!).get(NotesActivityViewModel::class.java)
        activityViewModel.addFakeNotes()

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

        arguments?.getLong(EXTRA_NOTEBOOK_ID)?.let {
            notebookId = it
        }

        if(notebookId != 0L) {
            notesViewModel.getNotebookLiveData()
                    .observe(this, Observer {
                        it?.let {
                            activityViewModel.setTitle(it.name)
                        }
                    })
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = RvAdapter()
        rvNotes.adapter = adapter

        notesViewModel.getNotes(notebookId).observe(this, Observer {
            list -> adapter.setData(list)
        })

        adapter.listener = { entity : BaseEntity ->
            activityViewModel.openNote((entity as Note).id)
        }

        fabAddNote.setOnClickListener{
            val newNoteId = notesViewModel.createNewNote()
            activityViewModel.openNote(newNoteId)
        }
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.setTitle( getString(R.string.notes))
    }

    companion object {

        const val EXTRA_NOTEBOOK_ID = "notebook_id"

        fun newInstance(notebookId : Long) : NotesListFragment {
            val args = Bundle()
            args.putLong(EXTRA_NOTEBOOK_ID, notebookId)

            val fragment = NotesListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
