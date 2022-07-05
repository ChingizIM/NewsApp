package com.example.newsapp.models

import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.RowId

@Entity // таблица( первый компонент )
data class News(
    @PrimaryKey(autoGenerate = true)
    // @PrimaryKey - отмечаем,что это поле будет ключем
    // (autoGenerate) - генерирование уникальных id
    val id: Int,
    var title: String,
    val createdAt: Long
) : Serializable {
    fun toString(function: () -> String) {
        TODO("Not yet implemented")
    }

    operator fun invoke(listContent: Int, nothing: Nothing?): Nothing? {
        TODO("Not yet implemented")
    }

}
