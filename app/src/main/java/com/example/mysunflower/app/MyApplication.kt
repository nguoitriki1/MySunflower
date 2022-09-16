package com.example.mysunflower.app

import android.app.Application
import com.example.mysunflower.database.PlantsDatabase


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createDatabase()
    }

    private fun createDatabase() {
        PlantsDatabase.getInstance(this)
    }
}