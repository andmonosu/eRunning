package com.andmonosu.erunning.domain

import com.andmonosu.erunning.data.UserRepository
import com.andmonosu.erunning.data.model.User

class GetUserByUsername () {

    private val repository = UserRepository()

    suspend operator fun invoke(username: String): User = repository.getUserByUsername(username)
}