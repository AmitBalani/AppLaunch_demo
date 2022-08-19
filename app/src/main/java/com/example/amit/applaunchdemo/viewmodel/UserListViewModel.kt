package com.example.amit.applaunchdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.amit.applaunchdemo.database.entities.UserModel
import com.example.amit.applaunchdemo.repository.UserListRepository

class UserListViewModel(private val userListRepository: UserListRepository) : ViewModel() {

    suspend fun insertUser(userModel: UserModel) = userListRepository.insertUser(userModel)

    suspend fun updateUser(userModel: UserModel) = userListRepository.updateUser(userModel)

    suspend fun deleteUser(userModel: UserModel) = userListRepository.deleteUser(userModel)

    suspend fun deleteUserById(id: Int) = userListRepository.deleteUserById(id)
    
    fun getAllUsers() = userListRepository.getAllUsers()
}