package com.anandarh.githubuserapp.ui.activities

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.databinding.ActivityReminderBinding
import com.anandarh.githubuserapp.models.ReminderModel
import com.anandarh.githubuserapp.utilities.AlarmReceiver
import com.anandarh.githubuserapp.utilities.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val alarmReceiver: AlarmReceiver = AlarmReceiver()
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferences(this@ReminderActivity)

        initializeUI()
        setOnClickAction()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initializeUI() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.reminder)
        }

        val savedReminder = sharedPreferences.getReminder()
        if (savedReminder != null) {
            binding.tvTimer.text = savedReminder.time
            binding.reminderMessage.setText(savedReminder.message)

            Toast.makeText(this, "Reminder is active", Toast.LENGTH_LONG).show()
        }
    }

    private fun setOnClickAction() {
        binding.btnTimer.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                binding.tvTimer.text =
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)
            }

            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.btnSetReminder.setOnClickListener {
            val message = binding.reminderMessage
            val timer = binding.tvTimer.text.toString()

            val messageString = message.text.trim().toString()

            if (messageString.isEmpty()) {
                message.error = ""
            } else {
                alarmReceiver.setReminder(
                    this,
                    timer, messageString
                )

                sharedPreferences.setReminder(
                    ReminderModel(
                        time = timer,
                        message = messageString
                    )
                )
            }

        }

        binding.btnCancelReminder.setOnClickListener {
            alarmReceiver.cancelReminder(this)
            sharedPreferences.removeReminder()
            binding.tvTimer.setText(R.string.alarm_time)
            binding.reminderMessage.text.clear()
        }
    }
}