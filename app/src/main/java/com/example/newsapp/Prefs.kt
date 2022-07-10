package com.example.newsapp

import android.content.Context

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        // context - для вызова системного метода(getSharedPreferences), у которой нет полномочий в этом классе
        // SharePreferences - для простого хранения настроек.Если информации не так много, то гораздо выгоднее использовать SharedPreferences.
        // "settings" - название файла. Внутри Internal Storege(где храняться данные)
        // MODE_PRIVATE - только приложение имеет доступ к настройкам
    fun saveState(text: String) {

        preferences.edit().putBoolean("isShown", true).apply()
        // saveState - название метода хранение информации
        // edit - открываем хранилище для редактирование
        // putBoolean - видел,не видел информацию
        // "isShown" - был показан
        // apply - сохранение информации

            // Следующий шаг:
            // В BordFragmente (onViewCreated) для привязки кнопке "START"
    } // создали метод для сохранения информации

    fun iShown(): Boolean {
        // создали метод для чтения
        return preferences.getBoolean("isShown", false)
        // return - возвращение значения
        // "isShown - получить(прочитать) значение по этому ключу
        // false - в этом случае, если этот метот не найдет ключь "isShown", он возвратить значение по умолчанию

            // Следующий шаг:
            // MainActivity (onCreate)
    }
}