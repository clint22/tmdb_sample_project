package com.clint.tmdb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clint.tmdb.R
import com.clint.tmdb.data.local.MovieList
import com.clint.tmdb.others.POSTER_PATH_BASE_URL
import com.clint.tmdb.others.convertStringToDate
import com.clint.tmdb.others.formatToViewDateDefaults

class MovieListAdapter(
    private val movieList: List<MovieList>?,
    var onMovieClicked: (Int) -> Unit,
    private val context: Context
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_movie_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = movieList?.get(position)
        holder.textViewTitle.text = itemsViewModel?.title ?: "Title not available"
        holder.textViewReleaseDate.text = convertToDisplayDateFormat(itemsViewModel?.releaseDate)
        holder.textViewRating.text = itemsViewModel?.rating.toString()
        holder.textViewTotalVotes.text = itemsViewModel?.totalCount.toString()

//        Concatenating the base poster path with the movie poster path
        val posterImageCompletePath = POSTER_PATH_BASE_URL + itemsViewModel?.posterPath

        Glide
            .with(context)
            .load(posterImageCompletePath)
            .centerCrop()
            .placeholder(R.drawable.no_image_view_holder)
            .into(holder.imageViewPoster)

        holder.cardViewMovieDetails.setOnClickListener {
            itemsViewModel?.id?.let { it1 -> onMovieClicked(it1) }
        }
    }

//    Converts the recieved date format to a display date format using the extension functions.
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
        val cardViewMovieDetails: CardView = itemView.findViewById(R.id.cardViewMovieDetails)

    }
}