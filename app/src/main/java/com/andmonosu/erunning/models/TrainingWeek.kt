package com.andmonosu.erunning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrainingWeek(var days:MutableList<TrainingDay> = mutableListOf()):Parcelable
