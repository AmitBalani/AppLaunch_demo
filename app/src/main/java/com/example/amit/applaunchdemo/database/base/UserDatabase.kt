package com.example.amit.applaunchdemo.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.amit.applaunchdemo.database.dao.UserDao
import com.example.amit.applaunchdemo.database.entities.UserModel


@Database(entities = [UserModel::class], exportSchema = false, version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private const val DB_NAME = "user_db"
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}