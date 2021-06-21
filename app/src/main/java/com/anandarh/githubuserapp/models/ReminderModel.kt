package com.anandarh.githubuserapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReminderModel(
    val time: String,
    val message: String,
): Parcelable