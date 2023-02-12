package com.workshop.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.workshop.practice.R
import com.workshop.practice.database.BookEntity
import com.workshop.practice.model.Book

class FavouritesRecyclerAdapter(val context: Context, val bookList: List<BookEntity>): RecyclerView.Adapter<FavouritesRecyclerAdapter.FavouritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size

    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val book=bookList[position]
        holder.tvName.text= book.bookName
        holder.tvAuthor.text= book.bookAuthor
        holder.tvRating.text= book.bookRating
        holder.tvPrice.text= book.bookPrice
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBook)
    }
    class FavouritesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvName: TextView= view.findViewById(R.id.tvName)
        val tvAuthor: TextView= view.findViewById(R.id.tvAuthor)
        val tvPrice: TextView= view.findViewById(R.id.tvPrice)
        val tvRating: TextView= view.findViewById(R.id.tvRating)
        val imgBook: ImageView= view.findViewById(R.id.imgBook)
    }
}