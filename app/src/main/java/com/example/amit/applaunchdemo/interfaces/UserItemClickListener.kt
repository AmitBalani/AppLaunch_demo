package com.example.amit.applaunchdemo.interfaces

import com.example.amit.applaunchdemo.database.entities.UserModel


interface UserItemClickListener {
    fun userItemClick(position: Int, userModel: UserModel)
}