package com.andmonosu.erunning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Training(
    var trainingWeeks:MutableList<TrainingWeek> = mutableListOf(), val name:String="None",
    val isActive: Boolean = false) : Parcelable

