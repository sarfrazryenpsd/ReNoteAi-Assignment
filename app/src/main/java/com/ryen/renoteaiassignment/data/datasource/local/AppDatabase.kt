package com.ryen.renoteaiassignment.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Add this line
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

}