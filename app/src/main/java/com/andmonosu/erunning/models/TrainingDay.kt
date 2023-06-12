package com.andmonosu.erunning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.DayOfWeek

@Parcelize
data class TrainingDay(val day:DayOfWeek, val type: SessionType, val time:Number = -1, val distance:Double = -1.0, val pace:Double = -1.0):Parcelable