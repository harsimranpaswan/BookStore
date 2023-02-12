package com.workshop.practice.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.workshop.practice.R
import com.workshop.practice.activity.DescriptionActivity
import com.workshop.practice.model.Book

class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard, parent, false)

        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book =itemList[position]
        holder.name.text=book.bookName
        holder.author.text= book.bookAuthor
        holder.price.text= book.bookPrice
        holder.rating.text= book.bookRating
  //    holder.image.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.image)

        holder.lay.setOnClickListener{

            val intent= Intent(context, DescriptionActivity:: class.java)
            intent.putExtra("book_id", book.bookId)
            context.startActivity(intent)
            Toast.makeText(context, "clicked on ${holder.name.text}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size

    }
    //viewHolder
    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.tvRecyclerName)
        val rating: TextView = view.findViewById(R.id.tvRecyclerRating)
        val author: TextView = view.findViewById(R.id.tvRecyclerAuthor)
        val price: TextView = view.findViewById(R.id.tvRecyclerPrice)
        val image: ImageView = view.findViewById(R.id.recyclerImage)
        val lay: LinearLayout= view.findViewById(R.id.recyclerID)
    }
    //ViewHolder Finish


}

