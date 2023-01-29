package com.workshop.practice.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.workshop.practice.R
import com.workshop.practice.adapter.DashboardRecyclerAdapter
import com.workshop.practice.model.Book
import com.workshop.practice.util.ConnectionManager

class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var checkC: Button
    val bookList= arrayListOf("Forever", "Teenage Dreams", "Fifty Shades of Grey", "Fifty shades Darker", "Fifty shades Freed",
        "The three mistakes of my life", "2 states", "Half Girlfriend", "13 Reasons Why", "Rich dad Poor dad")
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
    )


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)
        checkC=view.findViewById(R.id.checkC)

        checkC.setOnClickListener{
            if(ConnectionManager().checkConnectivity(activity as Context)){
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Connection Found")

                dialog.create()
                dialog.show()
            }
            else{
                val dialog=AlertDialog.Builder(activity as Context)
                dialog.setTitle("Failed")
                dialog.setMessage("No Connection Found")

                dialog.create()
                dialog.show()
            }
        }

        layoutManager=LinearLayoutManager(activity)
        recyclerAdapter= DashboardRecyclerAdapter(activity as Context, bookInfoList)
        recyclerDashboard.adapter=recyclerAdapter
        recyclerDashboard.layoutManager=layoutManager
//

        val queue= Volley.newRequestQueue(activity as  Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener{
            //Responses
            println("Response is $it")

        }, Response.ErrorListener{
            //Errors
            println("Error is $it")

        }){
            //anonymous object, to override the methods with very few modifications
            //Headers tells about type of content, also sends unique token

            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String, String>()
                headers["Content-type"]="application/json"
                headers["token"]="3008219e912e2e"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

        return view
    }

}