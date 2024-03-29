package com.andmonosu.erunning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Training(
    var trainingWeeks:MutableList<TrainingWeek> = mutableListOf(), var name:String="None",
    var isActive: Boolean = false) : Parcelable

