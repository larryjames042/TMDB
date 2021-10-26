package com.codigo.tmdb.ui.movielist.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigo.tmdb.R
import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.databinding.FragmentMoviesBinding
import com.codigo.tmdb.network.NetworkState
import com.codigo.tmdb.ui.moviedetail.MovieDetailActivity
import com.codigo.tmdb.ui.movielist.FavouriteButtonClick
import com.codigo.tmdb.ui.movielist.MovieItemClick
import com.codigo.tmdb.ui.movielist.MoviesListAdapter
import com.codigo.tmdb.viewmodel.UpcomingMoviesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UpcomingMoviesFragment : Fragment() {

    private val upcomingMovieViewViewModel : UpcomingMoviesFragmentViewModel by viewModels()

    private var movieListAdapter : MoviesListAdapter?  = null

    lateinit var binding : FragmentMoviesBinding


    // this object will be used to remove favourite
    private var mMovie : Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        upcomingMovieViewViewModel.movies.observe(viewLifecycleOwner, Observer {
            Timber.tag("dataresponseup").d(it.toString())
            it?.apply {
                // fill adapter with data'
                movieListAdapter?.submitList(it)
            }
        })

        /**
         * check the network reponse state
         */
        upcomingMovieViewViewModel.networkResponseState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            // hide progress bar
            binding.progress.visibility = View.GONE
            when(it){
                is NetworkState.Success -> {
                    Toast.makeText(activity, "data has been successfully updated", Toast.LENGTH_SHORT).show()
                }
                is NetworkState.Error  -> {
                    Toast.makeText(activity, "Error Occurs", Toast.LENGTH_SHORT).show()
                }
                is NetworkState.NetworkException -> {
                    Toast.makeText(activity, "Unable to update Data", Toast.LENGTH_SHORT).show()
                }
            }
        })

        /**
         * observe favourite insert result
         */
        upcomingMovieViewViewModel.favInsertResult.observe(viewLifecycleOwner, Observer {
            if(it.toInt()== -1){
                // already exist in database
                Toast.makeText(activity, "Removed from favourite", Toast.LENGTH_SHORT).show()
                mMovie?.run {
                    upcomingMovieViewViewModel.removeFavouriteData(mMovie!!)
                }
            }else{
                // successfully added
                Toast.makeText(activity, "Added to Favourite", Toast.LENGTH_SHORT).show()
            }
        })

    }

    /**
     * helper methods for recyclerview
     */
    private fun setUpRecyclerView(){
        movieListAdapter = MoviesListAdapter(MovieItemClick { img, movie->

            // Movie item click
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(Utils.MOVIE_OBJECT, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                (img as View?)!!, getString(R.string.share_image_name)
            )
            startActivity(intent, options.toBundle())

        }, FavouriteButtonClick { movie ->

            mMovie = movie

            // favourite button click
            upcomingMovieViewViewModel.insertFavouriteData(movie)
        })
        binding.rvMovies.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.adapter = movieListAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            UpcomingMoviesFragment()
    }
}