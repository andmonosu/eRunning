package com.andmonosu.erunning.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainingWeek(var days:MutableList<TrainingDay> = mutableListOf(), val name:String):Parcelable
