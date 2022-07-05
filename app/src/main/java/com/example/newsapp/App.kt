package com.example.newsapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapp.room.AppDadaBase

class App : Application() {
    companion object {
        lateinit var database: AppDadaBase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDadaBase::class.java, "database")
            .allowMainThreadQueries()
            .build()
        //.allowMainThreadQueries() разрещить запросы на главном потоке
        // Далее идем в NewsFragment и присваем id=0
        //preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        // "settings" - файл хронящий SharePreferences
        // "database" - файл хронящий базу данных
    }
}