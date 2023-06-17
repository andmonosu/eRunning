package com.andmonosu.erunning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.DayOfWeek

@Parcelize
data class TrainingDay(val day:DayOfWeek, var type: SessionType, var time:Number = -1, var distance:Double = -1.0, var pace:Double = -1.0):Parcelable