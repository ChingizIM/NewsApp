package com.example.newsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.models.News

@Dao // Второй компонент
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): List<News>
    // это метод чтения
    // теперь нужно вызвать getAll и он нам влозвращаеть список(List) News, в HomeFragmente
    @Insert
    fun insert(news: News)

    fun deleteItem(news: News)
    fun sortAll(): News
    // @Insert - Выполняет запись модельки(news) в таблицу
}