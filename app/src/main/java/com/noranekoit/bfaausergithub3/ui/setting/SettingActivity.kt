package com.noranekoit.bfaausergithub3.ui.setting

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.noranekoit.bfaausergithub3.data.model.Reminder
import com.noranekoit.bfaausergithub3.databinding.ActivitySettingBinding
import com.noranekoit.bfaausergithub3.preference.ReminderPreference
import com.noranekoit.bfaausergithub3.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var alarmReceiver: AlarmReceiver
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reminderPreference = ReminderPreference(this)
        if (reminderPreference.getReminder().isReminder){
            binding.switchAlarm.isChecked = true
        }
        alarmReceiver = AlarmReceiver()


        binding.cvSetting.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(this,"RepeatingAlarm","09:00","Ugit App 3 reminder")
            } else{
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminder = Reminder()

        reminder.isReminder = b
        reminderPreference.setReminder(reminder)
    }
}