package com.example.kotlin_mvvm_retrofit


import android.graphics.Color
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var editsearch: SearchView
    lateinit var recycler: RecyclerView
    private var movieList: ArrayList<Android>? = null
    private var movieAdapter: MyAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recyclerview) as RecyclerView
        editsearch = findViewById(R.id.country_search) as SearchView
        recycler!!.layoutManager = LinearLayoutManager(this)
        movieAdapter = MyAdapter()
        recycler!!.adapter = movieAdapter
        movieList = ArrayList()
        val searchIcon = country_search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)

        val cancelIcon = country_search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        val textView = country_search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
        editsearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter!!.filter.filter(newText)
                return false
            }

        })
        getAndroidVersion()
    }
    private fun getAndroidVersion() {
        Log.e("getAndroidVersion", "Yes")
        val mAndroidViewModel =
            ViewModelProviders.of(this@MainActivity).get(AndroidViewModel::class.java)
        mAndroidViewModel.getAndroidData()?.observe(this, Observer<List<Android>> { androidList ->
            movieList = androidList as ArrayList<Android>?
            Log.e("list", androidList?.size.toString())
            movieList?.let { movieAdapter?.setMovieList(applicationContext, it) }

            Log.e("MMMM", movieList?.size.toString())

        //    movieAdapter!!.setMovieList(applicationContext, androidList as ArrayList<Android>)
//            recyclerview.adapter =
//                EmpAdapter(
//                    this@MainActivity,
//                    androidList as ArrayList<Android>,
//                    object : ItemClickListener {
//                        override fun onItemClick(pos: Int) {
//                            Toast.makeText(
//                                applicationContext,
//                                "item $pos clicked",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    })
        })

    }

}
