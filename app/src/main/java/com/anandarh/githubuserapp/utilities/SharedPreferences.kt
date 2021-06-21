package com.anandarh.githubuserapp.utilities

import android.content.Context
import com.anandarh.githubuserapp.models.ReminderModel
import com.google.gson.Gson

internal class SharedPreferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "github_pref"
        private const val REMINDER = "reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun setReminder(value: ReminderModel) {
        val data = gson.toJson(value, ReminderModel::class.java)
        val editor = preferences.edit()
        editor.putString(REMINDER, data)
        editor.apply()
    }

    fun getReminder(): ReminderModel? {
        val data = preferences.getString(REMINDER, null)
        return if (data.isNullOrEmpty()) null else gson.fromJson(data, ReminderModel::class.java)
    }

    fun removeReminder() {
        val editor = preferences.edit()
        editor.remove(REMINDER)
        editor.apply()
    }
}