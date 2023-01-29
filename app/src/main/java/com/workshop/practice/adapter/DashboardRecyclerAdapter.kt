package com.workshop.practice.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.workshop.practice.R
import com.workshop.practice.model.Book

class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard, parent, false)

        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book =itemList[position]
        holder.name.text=book.BookName
        holder.author.text= book.BookAuthor
        holder.price.text= book.BookPrice
        holder.rating.text= book.BookRating
        holder.image.setImageResource(book.BookImg)
        holder.lay.setOnClickListener{
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

