package com.anandarh.githubuserapp.ui.activities

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.databinding.ActivityReminderBinding
import com.anandarh.githubuserapp.utilities.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private val alarmReceiver: AlarmReceiver = AlarmReceiver()

    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            if (message.text.trim().isEmpty()) {
                message.error = ""
            } else {
                alarmReceiver.setReminder(
                    this,
                    timer, message.text.trim().toString()
                )
            }

        }

        binding.btnCancelReminder.setOnClickListener { alarmReceiver.cancelReminder(this) }
    }
}