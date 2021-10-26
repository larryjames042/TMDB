package com.codigo.tmdb.ui.moviedetail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.codigo.tmdb.R
import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.databinding.ActivityMovieDetailBinding
import com.codigo.tmdb.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityMovieDetailBinding

    private val movieViewModel : MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent?.run {
            val movie = this.getSerializableExtra(Utils.MOVIE_OBJECT) as Movie
            movieViewModel.setMovieData(movie)
        }

        movieViewModel.getFavouriteStatusText()

        // observe the status if it is already in database
        movieViewModel.isInFavouriteList.observe(this, Observer { isInDatabase ->

            /**
             * hardcoded string. could be stored in string resource
              */
            changeFavouriteButtonText(isInDatabase)

        })


        // observe fav insert status
        movieViewModel.favInsertResult.observe(this, Observer {
            movieViewModel.getFavouriteStatusText()
        })


        movieViewModel.favRemoveResult.observe(this, Observer {
            movieViewModel.getFavouriteStatusText()
        })

        // observe fav remove status

        // databinding
        binding.lifecycleOwner = this
        binding.movie = movieViewModel

    }


    private fun changeFavouriteButtonText(isInDatabase : Boolean){
        binding.btnFav.text = if(isInDatabase) "Remove From Favourite" else "Add to Favourite"
    }

    // menu item click.. also back button to work
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            supportFinishAfterTransition()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}