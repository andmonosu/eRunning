package com.andmonosu.erunning.views

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.andmonosu.erunning.R
import com.google.android.gms.maps.model.LatLng

class MVP {
}

data class Ui(
    val formattedPace: String,
    val formattedDistance: String,
    val currentLocation: LatLng?,
    val userPath: List<LatLng>
) {

    companion object {

        val EMPTY = Ui(
            formattedPace = "",
            formattedDistance = "",
            currentLocation = null,
            userPath = emptyList()
        )
    }
}