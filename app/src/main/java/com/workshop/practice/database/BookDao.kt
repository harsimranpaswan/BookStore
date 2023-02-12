package com.workshop.practice.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.workshop.practice.model.Book

@Dao
interface BookDao {
// insert and delete functions are taken care of by the library
// whereas display and other functions are needed to be mentioned
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE book_id=:bookId")
    fun getBookById(bookId: String): BookEntity
}