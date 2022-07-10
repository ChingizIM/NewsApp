package com.example.newsapp.room

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    fun deleteItem(news: News)

    @Query("SELECT * FROM news ORDER BY createdAt DESC")
    fun sortAll(): List<News>

    @Query("SELECT * FROM news WHERE title LIKE '%'|| :search || '%'")
    fun getSearch(search: String?): List<News>
   // @Query("SELECT * FROM news ORDER BY title ASC")
    // fun sort(): List<News>
    //fun sortAll(): News
    // @Insert - Выполняет запись модельки(news) в таблицу
}