package com.andmonosu.erunning.data

import com.andmonosu.erunning.data.model.User
import com.andmonosu.erunning.data.model.UserProvider
import com.andmonosu.erunning.data.network.UserService

class UserRepository {

    private val api = UserService()

    suspend fun getUserByUsername(username:String): User {
        val response = api.getUserByUsername(username)
        UserProvider.user = response
        return response
    }

    suspend fun saveUser(username:String, user: User) {
        api.saveUser(username, user)
    }
}