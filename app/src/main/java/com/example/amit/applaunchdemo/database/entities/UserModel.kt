package com.example.amit.applaunchdemo.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Int? = null,
    @ColumnInfo(name = "email_address")
    var emailAddress: String? = null,
    @ColumnInfo(name = "first_name")
    var firstName: String? = null,
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
)