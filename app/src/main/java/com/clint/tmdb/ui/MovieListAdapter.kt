package com.clint.tmdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clint.tmdb.R
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.others.POSTER_PATH_BASE_URL
import com.clint.tmdb.others.convertStringToDate
import com.clint.tmdb.others.formatToViewDateDefaults

class MovieListAdapter(private val movieList: List<MovieList>?, private val context: Context) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_movie_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = movieList?.get(position)
        holder.textViewTitle.text = ItemsViewModel?.title ?: "Title not available"
        holder.textViewReleaseDate.text = convertToDisplayDateFormat(ItemsViewModel?.releaseDate)
        holder.textViewRating.text = ItemsViewModel?.rating.toString()
        holder.textViewTotalVotes.text = ItemsViewModel?.totalCount.toString()

        val posterImageCompletePath = POSTER_PATH_BASE_URL + ItemsViewModel?.posterPath

        Glide
            .with(context)
            .load(posterImageCompletePath)
            .centerCrop()
            .placeholder(R.drawable.no_image_view_holder)
            .into(holder.imageViewPoster)

    }

    private fun convertToDisplayDateFormat(releaseDate: String?): CharSequence? {
        val date = releaseDate?.convertStringToDate()
        return date?.formatToViewDateDefaults()
    }

    override fun getItemCount(): Int {
        return movieList?.size!!
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewReleaseDate: TextView = itemView.findViewById(R.id.textViewReleaseDate)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
        val textViewTotalVotes: TextView = itemView.findViewById(R.id.textViewTotalVotes)

    }
}