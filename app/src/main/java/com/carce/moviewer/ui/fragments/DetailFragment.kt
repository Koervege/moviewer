package com.carce.moviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carce.moviewer.R
import com.carce.moviewer.databinding.FragmentDetailBinding
import com.carce.moviewer.networkService.ApiURL
import com.carce.moviewer.viewModel.ListViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private lateinit var _binding: FragmentDetailBinding
    private lateinit var listViewModel: ListViewModel
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProvider(this.activity as ViewModelStoreOwner).get()

        lifecycleScope.launchWhenCreated {
            listViewModel.movieToBeDetailed.collect {
                Picasso.get().load("${ApiURL.IMAGE_URL}${it.image}").into(binding.movieDetailPoster)
                binding.movieDetailPoster.contentDescription = "poster for ${it.title}"
                binding.movieDetailSummary.text = it.overview
            }
        }
    }
}