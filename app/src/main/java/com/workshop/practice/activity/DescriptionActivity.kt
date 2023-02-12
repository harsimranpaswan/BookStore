package com.workshop.practice.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.content.AsyncTaskLoader
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.workshop.practice.R
import com.workshop.practice.adapter.DashboardRecyclerAdapter
import com.workshop.practice.database.BookDatabase
import com.workshop.practice.database.BookEntity
import com.workshop.practice.model.Book
import com.workshop.practice.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    lateinit var descriptionImage: ImageView
    lateinit var descriptionName: TextView
    lateinit var descriptionRating: TextView
    lateinit var descriptionAuthor: TextView
    lateinit var descriptionPrice: TextView
    lateinit var descriptionText: TextView
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var addToFav: Button

    var bookId: String? = "100"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        descriptionImage = findViewById(R.id.descriptionImage)
        descriptionName = findViewById(R.id.tvDescriptionName)
        descriptionRating = findViewById(R.id.tvDescriptionRating)
        descriptionAuthor = findViewById(R.id.tvDescriptionAuthor)
        descriptionPrice = findViewById(R.id.tvDescriptionPrice)
        descriptionText = findViewById(R.id.descriptionText)
        addToFav = findViewById(R.id.addToFav)
        progressBar = findViewById(R.id.progressBar)
        progressLayout = findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE

        if (intent != null)
            bookId = intent.getStringExtra("book_id")
        else {
            Toast.makeText(this@DescriptionActivity, "An Error Occured", Toast.LENGTH_SHORT).show()
            finish()
        }
        if (bookId == "100") {
            Toast.makeText(this@DescriptionActivity, "An Error Occured", Toast.LENGTH_SHORT).show()
            finish()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)) {

            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                    //Responses
                    try {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val bookJSONObject = it.getJSONObject("book_data")
                            val bookImageUrl = bookJSONObject.getString("image")
                            Picasso.get().load(bookImageUrl)
                                .error(R.drawable.default_book_cover).into(descriptionImage)
                            descriptionName.text = bookJSONObject.getString("name")
                            descriptionAuthor.text = bookJSONObject.getString("author")
                            descriptionRating.text = bookJSONObject.getString("rating")
                            descriptionPrice.text = bookJSONObject.getString("price")
                            descriptionText.text = bookJSONObject.getString("description")

                            val bookEntity = BookEntity(
                                bookId?.toInt() as Int,
                                descriptionName.text.toString(),
                                descriptionAuthor.text.toString(),
                                descriptionRating.text.toString(),
                                descriptionPrice.text.toString(),
                                descriptionText.text.toString(),
                                bookImageUrl
                            )
                            //creating object of DBAsyncTask Class
                            val checkFav = DBAsyncTask(applicationContext, bookEntity, 1).execute()
                            val isFav = checkFav.get()
                            if (isFav == true) {
                                addToFav.text = "Remove From Favourites"
                                val favColor =
                                    ContextCompat.getColor(applicationContext, R.color.dark_orange)
                                addToFav.setBackgroundColor(favColor)
                            } else {
                                addToFav.text = "Add To Favourites"
                                val nonFavColor =
                                    ContextCompat.getColor(applicationContext, R.color.orange)
                                addToFav.setBackgroundColor(nonFavColor)
                            }

                            addToFav.setOnClickListener {
                                if (!DBAsyncTask(applicationContext, bookEntity, 1).execute()
                                        .get()
                                ) {
                                    //when book is to be added
                                    val async =
                                        DBAsyncTask(applicationContext, bookEntity, 2).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Book Added To Favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        addToFav.text = "Remove From Favourites"
                                        val favColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.dark_orange
                                        )
                                        addToFav.setBackgroundColor(favColor)
                                    } else {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Some Error Occured",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val async =
                                        DBAsyncTask(applicationContext, bookEntity, 3).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Book Removed From Favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        addToFav.text = "Add To Favourites"
                                        val noFavColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.orange
                                        )
                                        addToFav.setBackgroundColor(noFavColor)
                                    } else {
                                        Toast.makeText(
                                            this@DescriptionActivity,
                                            "Some Error Occured",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@DescriptionActivity,
                                "Error Occured",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Error Occured",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    //Errors
                    if (this@DescriptionActivity != null) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Error Occured",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }


                }) {
                    //anonymous object, to override the methods with very few modifications
                    //Headers tells about type of content, also sends unique token

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "3008219e912e2e"
                        return headers
                    }
                }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Failed")
            dialog.setMessage("No Connection Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit App") { text, listener ->
                ActivityCompat.finishAffinity(this@DescriptionActivity)
            }

            dialog.create()
            dialog.show()
        }
    }

    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {

            when (mode) {
                1 -> {
                    //check
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null
                }
                2 -> {
                    //save
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    //remove
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true

                }

            }
            return false
        }

    }
}


