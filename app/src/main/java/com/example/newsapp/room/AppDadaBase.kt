package com.example.newsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.models.News

@Database(entities = [News::class], version = 1)
abstract class AppDadaBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    // @Database - Третий компонет
    // Чтобы записать запись в базу, нам нужно вызвать AppDataBase, у него будет newsDao, а у него Insert(которая принимает запись)
    // И для этого мы должны создать(вызвать) AppDataBase в App(application-приложение)
}