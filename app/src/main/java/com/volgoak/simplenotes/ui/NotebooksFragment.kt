package com.volgoak.simplenotes.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volgoak.simplenotes.NoteBook

import com.volgoak.simplenotes.R
import com.volgoak.simplenotes.RvAdapter
import com.volgoak.simplenotes.viewModel.NotesActivityViewModel
import kotlinx.android.synthetic.main.fragment_notebooks.*


/**
 * A simple [Fragment] subclass.
 */
class NotebooksFragment : Fragment() {

    lateinit var activityViewModel: NotesActivityViewModel
    lateinit var adapter : RvAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_notebooks, container, false)

        activityViewModel = ViewModelProviders.of(activity!!).get(NotesActivityViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNoteBooks.layoutManager = LinearLayoutManager(context)
        adapter = RvAdapter()
        adapter.listener = {baseEntity ->
            activityViewModel.openNoteBook((baseEntity as NoteBook).id)
        }

        rvNoteBooks.adapter = adapter

        activityViewModel.getNotebooks().observe(this, Observer { list -> adapter.setData(list) })
    }
}
