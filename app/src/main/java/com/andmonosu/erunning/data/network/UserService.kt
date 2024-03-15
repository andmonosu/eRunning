package com.andmonosu.erunning.data.network

import com.andmonosu.erunning.core.RetrofitHelper
import com.andmonosu.erunning.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {

    private val retrofit = RetrofitHelper.getRetrofit().create(UserApiClient::class.java)

    suspend fun getUserByUsername(username:String): User{
        return withContext(Dispatchers.IO){
            val response = retrofit.getUserByUsername(username)
            response.body() ?: User()
        }
    }

    suspend fun saveUser(username:String, user: User) {
        retrofit.saveUser(username, user)
    }

}