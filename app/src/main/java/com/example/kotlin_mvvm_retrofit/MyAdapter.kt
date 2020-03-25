package com.example.kotlin_mvvm_retrofit

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    private var movieList: List<Android>? = null
    private var movieListFiltered: List<Android>? = null
    private var context: Context? = null

    fun setMovieList(context: Context, movieList: List<Android>) {
        this.context = context
        if (this.movieList == null) {
            this.movieList = movieList
            this.movieListFiltered = movieList
            notifyItemChanged(0, movieListFiltered!!.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@MyAdapter.movieList!!.size
                }

                override fun getNewListSize(): Int {
                    return movieList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@MyAdapter.movieList!![oldItemPosition].name === movieList[newItemPosition].name
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {

                    val newMovie = this@MyAdapter.movieList!![oldItemPosition]

                    val oldMovie = movieList[newItemPosition]

                    return newMovie.name === oldMovie.name
                }
            })
            this.movieList = movieList
            this.movieListFiltered = movieList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = movieListFiltered!![position].name
        holder.title2.text = movieListFiltered!![position].apiLevel
    }

    override fun getItemCount(): Int {

        return if (movieList != null) {
            movieListFiltered!!.size
        } else {
            0
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    movieListFiltered = movieList
                } else {
                    val filteredList = ArrayList<Android>()
                    for (movie in movieList!!) {
                        if (movie.name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie)
                        }
                    }
                    movieListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = movieListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                movieListFiltered = filterResults.values as ArrayList<Android>

                notifyDataSetChanged()
            }
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var title: TextView
        internal var title2: TextView

        init {
            title = view.findViewById<View>(R.id.textView) as TextView
            title2 = view.findViewById<View>(R.id.textView2) as TextView
        }
    }
}


































