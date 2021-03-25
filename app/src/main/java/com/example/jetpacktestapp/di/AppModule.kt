package com.example.jetpacktestapp.di

import com.example.jetpacktestapp.App
import com.example.jetpacktestapp.MainActivitySubComponent
import com.example.jetpacktestapp.db.JetpackTestAppDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [MainActivitySubComponent::class])
class AppModule {

    @Provides
    @Singleton
    fun provideJetpackTestAppDb(app: App): JetpackTestAppDb {
        return JetpackTestAppDb.buildDb(app)
    }
}