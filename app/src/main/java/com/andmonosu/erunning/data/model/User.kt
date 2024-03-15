package com.andmonosu.erunning.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("age") val age: Number = 30,
    @SerializedName("email") val email: String = "example@mail.com",
    @SerializedName("gender") val gender: String = "Male",
    @SerializedName("height") val height: Number = 170,
    @SerializedName("lastName") val lastName: String = "Smith",
    @SerializedName("name") val name: String = "John",
    @SerializedName("sportActivity") val sportActivity: String = "nada",
    @SerializedName("weight") val weight: Number = 65)
