package com.workshop.practice.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import androidx.room.util.findColumnIndexBySuffix
import com.workshop.practice.R
import com.workshop.practice.adapter.FavouritesRecyclerAdapter
import com.workshop.practice.database.BookDatabase
import com.workshop.practice.database.BookEntity


class FavouritesFragment : Fragment() {
    lateinit var recyclerFavourites: RecyclerView
    lateinit var favouritesRecyclerAdapter: FavouritesRecyclerAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    var dbBookList= listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerFavourites= view.findViewById(R.id.recyclerFavourites)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout=view.findViewById(R.id.progressLayout)
        layoutManager=GridLayoutManager(activity as Context, 2)

        dbBookList= RetrieveFavourites(activity as Context).execute().get()

        if(dbBookList!=null && activity!=null){
            progressLayout.visibility=View.GONE
            favouritesRecyclerAdapter= FavouritesRecyclerAdapter(activity as Context, dbBookList)
            recyclerFavourites.adapter=favouritesRecyclerAdapter
            recyclerFavourites.layoutManager=layoutManager
        }

        return view
    }

    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void, List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context, BookDatabase:: class.java,"books-db" ).build()
            return db.bookDao().getAllBooks()
        }

    }


}