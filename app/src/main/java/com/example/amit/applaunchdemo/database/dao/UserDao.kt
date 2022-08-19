package com.example.amit.applaunchdemo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.amit.applaunchdemo.database.entities.UserModel


@Dao
interface UserDao {

    //Profiles
    @Query("SELECT * FROM user")
    fun getListOfAllUser(): LiveData<MutableList<UserModel>>

     @Insert
    fun insertUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Query("DELETE FROM user WHERE user_id = :id")
    fun deleteUserById(id: Int)

}