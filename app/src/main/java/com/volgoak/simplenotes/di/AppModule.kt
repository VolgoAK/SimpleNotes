package com.volgoak.simplenotes.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by alex on 4/12/18.
 */

@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() : Application = application
}