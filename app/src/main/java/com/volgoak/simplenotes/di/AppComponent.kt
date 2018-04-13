package com.volgoak.simplenotes.di

import com.volgoak.simplenotes.viewModel.NoteEditViewModel
import com.volgoak.simplenotes.viewModel.NotesActivityViewModel
import com.volgoak.simplenotes.viewModel.NotesViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alex on 4/12/18.
 */

@Singleton
@Component(modules = [AppModule::class, NotesModule::class])
interface AppComponent {
    fun inject(target: NotesActivityViewModel)
    fun inject(target: NoteEditViewModel)
    fun inject(target: NotesViewModel)
}