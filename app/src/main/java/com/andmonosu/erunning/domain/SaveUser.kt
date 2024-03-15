package com.andmonosu.erunning.domain

import com.andmonosu.erunning.data.UserRepository
import com.andmonosu.erunning.data.model.User

class SaveUser () {

    private val repository = UserRepository()

    suspend operator fun invoke(username: String, user: User) = repository.saveUser(username, user)
}