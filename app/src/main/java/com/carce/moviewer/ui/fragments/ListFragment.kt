package com.carce.moviewer.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.carce.moviewer.R
import com.carce.moviewer.adapter.PopularMoviesAdapter
import com.carce.moviewer.databinding.FragmentListBinding
import com.carce.moviewer.model.Movie
import com.carce.moviewer.networkService.RequestState
import com.carce.moviewer.viewModel.ListViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private lateinit var listViewModel: ListViewModel
    private lateinit var popularMoviesAdapter : PopularMoviesAdapter
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProvider(this.activity as ViewModelStoreOwner).get()

        popularMoviesAdapter = PopularMoviesAdapter(mutableListOf())
        binding.rvMovies.adapter = popularMoviesAdapter
        binding.rvMovies.layoutManager = GridLayoutManager(this.context, 3, GridLayoutManager.VERTICAL, false)

        binding.swipeRefreshList.post {
            listViewModel.getMovieList()
        }
        binding.swipeRefreshList.setOnRefreshListener {
            listViewModel.getMovieList()
        }
        beginCollectingFromVM()
    }

    private fun beginCollectingFromVM() {
        lifecycleScope.launchWhenCreated {
            listViewModel.requestState.collect {
                when (it) {
                    is RequestState.Loading -> {
                        binding.swipeRefreshList.isRefreshing = true
                    }
                    is RequestState.Failure -> {
                        Log.e("API Failure", it.e.message.toString())
                        Snackbar.make(
                            binding.swipeRefreshList,
                            "Something went wrong, please try again later",
                            2000).show()
                        binding.swipeRefreshList.isRefreshing = false
                    }
                    is RequestState.Success -> {
                        binding.swipeRefreshList.isRefreshing = false
                        val movieList = it.data as List<Movie>
                        popularMoviesAdapter = PopularMoviesAdapter(movieList as MutableList<Movie>)
                        popularMoviesAdapter.setItemClick { movie ->
                            listViewModel.updateMovieToBeDetailed(movie)
                            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundleOf("movieTitle" to movie.title))
                        }
                        binding.rvMovies.adapter = popularMoviesAdapter
                        popularMoviesAdapter.notifyDataSetChanged()
                    }
                    is RequestState.Empty -> {
                        println("Empty...")
                    }
                }
            }
        }
    }
}