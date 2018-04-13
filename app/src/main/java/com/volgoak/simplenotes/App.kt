package com.volgoak.simplenotes

import android.app.Application
import com.volgoak.simplenotes.di.AppComponent
import com.volgoak.simplenotes.di.AppModule
import com.volgoak.simplenotes.di.DaggerAppComponent
import com.volgoak.simplenotes.di.NotesModule
import timber.log.Timber

/**
 * Created by alex on 4/12/18.
 */
class App : Application() {

    private lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .notesModule(NotesModule())
                .build()

        Timber.plant(Timber.DebugTree())
    }

    fun getComponent() : AppComponent = component
}