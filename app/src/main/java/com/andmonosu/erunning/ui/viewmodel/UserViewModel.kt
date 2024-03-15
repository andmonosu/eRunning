package com.andmonosu.erunning.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andmonosu.erunning.data.model.User
import com.andmonosu.erunning.domain.GetUserByUsername
import com.andmonosu.erunning.domain.SaveUser
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {

    val userModel = MutableLiveData<User>()

    val emailString = MutableLiveData<String>()

    val isLoading = MutableLiveData<Boolean>()

    var getUserByUsername = GetUserByUsername()
    var saveUser = SaveUser()
    fun onCreate(username: String) {
        viewModelScope.launch {
            val result = getUserByUsername(username)
            emailString.postValue(result.email)
            userModel.postValue(result)
        }
    }

    fun saveUserModel(username: String, user: User){
        viewModelScope.launch {
            userModel.postValue(user)
            saveUser(username, User())
        }
    }
}