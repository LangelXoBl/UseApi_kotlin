package com.example.testandroid.ui.trendingWeek

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testandroid.R
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.databinding.FragmentTrendingWeekBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingWeekFragment : Fragment(), TopRatedMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentTrendingWeekBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TopRatedViewModel by navGraphViewModels(R.id.nav_graph){
        defaultViewModelProviderFactory
    }

    private lateinit var topRatedItemAdapter: TopRatedMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingWeekBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        viewModel.fetchTopRatedMovies.observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus){

                ResourceStatus.LOADING->{
                    Log.e("Fetch Top Rated Movies", "Loading")
                }

                ResourceStatus.SUCCESS ->{
                    Log.e("Fetch Top Rated Movies", "success data: ${it.data?.size}")
                    topRatedItemAdapter = TopRatedMovieItemAdapter(it.data!!,this@TrendingWeekFragment)
                    binding.rvMovies.adapter = topRatedItemAdapter
                }

                ResourceStatus.ERROR->{
                    Log.e("fetchPopularMovies", "Failure: ${it.message} ")
                    Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onMovieClick(movieEntity: MovieEntity) {
        val action = TrendingWeekFragmentDirections.actionTrendingWeekFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)
    }

}