package com.andmonosu.erunning.data.network

import com.andmonosu.erunning.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiClient {
    @GET("/users/{username}.json")
    suspend fun getUserByUsername(@Path("username")username: String): Response<User>

    @PUT("/users/{username}.json")
    suspend fun saveUser(@Path("username")username: String, @Body user: User): Response<Void>
}