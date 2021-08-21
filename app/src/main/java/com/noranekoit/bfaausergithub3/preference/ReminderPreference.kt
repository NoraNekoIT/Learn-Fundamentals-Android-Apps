package com.noranekoit.bfaausergithub3.preference

import android.content.Context
import com.noranekoit.bfaausergithub3.data.model.Reminder

class ReminderPreference(context: Context) {


    private val preference = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun setReminder(value: Reminder){
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value.isReminder)
        editor.apply()
    }

    fun getReminder():Reminder{
        val model =  Reminder()
        model.isReminder = preference.getBoolean(REMINDER,false)
        return model
    }

    companion object{
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }
}