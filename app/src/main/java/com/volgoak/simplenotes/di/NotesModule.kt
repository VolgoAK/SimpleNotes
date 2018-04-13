package com.volgoak.simplenotes.di

import android.app.Application
import com.volgoak.simplenotes.App
import com.volgoak.simplenotes.MyObjectBox
import com.volgoak.simplenotes.model.NotesProvider
import com.volgoak.simplenotes.model.NotesProviderImpl
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

/**
 * Created by alex on 4/12/18.
 */

@Module
class NotesModule {

    @Provides
    @Singleton
    fun provideBoxStore(application: Application) : BoxStore {
        return MyObjectBox.builder().androidContext(application as App).build()
    }

    @Provides
    @Singleton
    fun provideProvider(boxStore: BoxStore) : NotesProvider {
        return NotesProviderImpl(boxStore)
    }
}