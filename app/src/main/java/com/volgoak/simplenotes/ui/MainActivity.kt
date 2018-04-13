package com.volgoak.simplenotes.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.volgoak.simplenotes.R
import com.volgoak.simplenotes.R.id.drawerMain
import com.volgoak.simplenotes.viewModel.NotesActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar_layout.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var activityViewModel: NotesActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()

        activityViewModel = ViewModelProviders.of(this).get(NotesActivityViewModel::class.java)

        activityViewModel.commandCallBack.observe(this, Observer { command ->
            if(command != null) onCommand(command)
        })

        if (supportFragmentManager.findFragmentById(R.id.containerMain) == null)
            openFragment(NotesListFragment(), false)
    }

    private fun initNavigation() {
        setSupportActionBar(toolbar)
        drawerToggle = ActionBarDrawerToggle(this, drawerMain, toolbar,
                R.string.open_drawer, R.string.close_drawer)

        drawerMain.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navViewMain.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerMain.isDrawerOpen(GravityCompat.START)) {
            drawerMain.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun onCommand(command: NotesActivityViewModel.Command) {
        when(command) {
            NotesActivityViewModel.Command.OPEN_NOTE -> openNote(command.id)
            NotesActivityViewModel.Command.OPEN_NOTEBOOK -> openNotebook(command.id)
        }
    }

    private fun openNote(id: Long) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(NoteActivity.EXTRA_NOTE_ID, id)
        startActivity(intent)
    }

    private fun openNotebooksList() {
        openFragment(NotebooksFragment())
    }

    private fun openNotebook(id: Long) {
        openFragment(NotesListFragment.newInstance(id))
    }

    private fun openAllNotes() {
        openFragment(NotesListFragment.newInstance(0))
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.containerMain, fragment)

        if (addToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_notes -> openAllNotes()

            R.id.item_notebooks -> openNotebooksList()

        }

        drawerMain.closeDrawer(GravityCompat.START)
        return true
    }
}
