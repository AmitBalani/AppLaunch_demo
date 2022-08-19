package com.example.amit.applaunchdemo.database.dao

import androidx.room.*
import com.example.amit.applaunchdemo.database.entities.UserModel


@Dao
interface UserDao {

    //Profiles
    @Query("SELECT * FROM user")
    fun getListOfAllUser(): List<UserModel>

     @Insert
    fun insertUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

}