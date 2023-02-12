package com.workshop.practice.database

import androidx.room.Database
import androidx.room.RoomDatabase

//abstract class hides the implementation from the user
//abstract methods would never have default implementations
// non abstract methods would always have default implementations
//abstract class is similar to interface

@Database(entities = [BookEntity:: class], version = 1)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}