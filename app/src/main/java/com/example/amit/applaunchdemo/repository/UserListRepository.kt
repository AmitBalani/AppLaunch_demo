package com.example.amit.applaunchdemo.repository


import androidx.lifecycle.LiveData
import com.example.amit.applaunchdemo.database.base.UserDatabase
import com.example.amit.applaunchdemo.database.entities.UserModel


class UserListRepository(private val userDatabase: UserDatabase)  {

    fun insertUser(userModel: UserModel) = userDatabase.userDao().insertUser(userModel)

    fun updateUser(userModel: UserModel) = userDatabase.userDao().updateUser(userModel)

    fun deleteUser(userModel: UserModel) = userDatabase.userDao().deleteUser(userModel)

    fun deleteUserById(id: Int) = userDatabase.userDao().deleteUserById(id)

    fun getAllUsers(): LiveData<MutableList<UserModel>> = userDatabase.userDao().getListOfAllUser()

}