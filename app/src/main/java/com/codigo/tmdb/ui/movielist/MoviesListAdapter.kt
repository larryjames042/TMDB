package com.codigo.tmdb.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codigo.tmdb.R
import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.data.domain.Movie

class MoviesListAdapter(val itemClickCallback : MovieItemClick, val favButtonClickCallback : FavouriteButtonClick ) : ListAdapter<Movie, MoviesListAdapter.ViewHolder>(DIFF_CALLBACK){
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.movieTitle.text = movie.movieTitle
        holder.movieOverview.text = movie.movieOverview

        Glide.with(holder.itemView.context)
            .load(Utils.TMDB_IMAGE_BASE_URL+movie.moviePosterUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .override(200, 300)
            .centerCrop()
            .into(holder.imgPoster)

        // container click event
        holder.container.setOnClickListener{
            itemClickCallback.onContainerClick(holder.imgPoster, movie)
        }

        // favourite
        holder.imgButtonFav.setOnClickListener{
            favButtonClickCallback.onFavouriteClick(movie)
        }

    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val container = v.findViewById<ViewGroup>(R.id.container)
        val movieTitle = v.findViewById<TextView>(R.id.txt_title)
        val movieOverview = v.findViewById<TextView>(R.id.txt_overview)
        val imgPoster = v.findViewById<ImageView>(R.id.img_movie)
        val imgButtonFav = v.findViewById<ImageButton>(R.id.img_button_fav)
    }

}

class MovieItemClick( val block : (ImageView, Movie) -> Unit){
    fun onContainerClick(img: ImageView, movie: Movie) = block(img, movie)
}

class FavouriteButtonClick(val block : (Movie) -> Unit){
    fun onFavouriteClick(movie: Movie) = block(movie)
}