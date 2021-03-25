package com.example.jetpacktestapp

import android.app.Application
import com.example.jetpacktestapp.di.AppComponent
import com.example.jetpacktestapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        initAppComponent()

        appComponent.inject(this)
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent
            .factory()
            .create(this)
    }

    companion object {
        private lateinit var app: App
        fun get(): App = app
    }
}