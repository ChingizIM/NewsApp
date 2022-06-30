package com.example.newsapp.models

import android.icu.text.CaseMap
import java.io.Serializable

data class News(
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
