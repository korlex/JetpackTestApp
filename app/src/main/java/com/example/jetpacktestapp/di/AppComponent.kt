package com.example.jetpacktestapp.di

import android.content.Context
import com.example.jetpacktestapp.App
import com.example.jetpacktestapp.MainActivitySubComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }

    fun mainActivitySubComponent(): MainActivitySubComponent.Factory
    fun inject(app: App)
}