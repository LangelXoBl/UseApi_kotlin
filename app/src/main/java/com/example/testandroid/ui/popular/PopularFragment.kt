package com.example.testandroid.ui.popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.R
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.data.model.Movie
import com.example.testandroid.data.model.ResourceStatus
import com.example.testandroid.databinding.FragmentPopularBinding
import com.example.testandroid.utils.Paging
import com.example.testandroid.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PopularFragment : Fragment(), PopularMovieItemAdapter.OnMovieClickListener {

    private var _binding: FragmentPopularBinding? = null

    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by navGraphViewModels(R.id.nav_graph) {
        defaultViewModelProviderFactory
    }

    private lateinit var popularMovieItemAdapter: PopularMovieItemAdapter
//Global
    private var page=1
    private  var loading=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        viewModel.fetchPopularMovies(page).observe(viewLifecycleOwner, Observer {
            when (it.resourceStatus) {
                ResourceStatus.LOADING -> {
                    Log.e("fetchPopularMovies", "Loading")
                }
                ResourceStatus.SUCCESS  -> {
                    Log.e("fetchPopularMovies", "Success data: ${it.data?.size}")
                    binding.total.text= it.data?.size.toString()
                    if (::popularMovieItemAdapter.isInitialized) {
                        popularMovieItemAdapter.addMovies(it.data!!)
                    } else {
                        popularMovieItemAdapter = PopularMovieItemAdapter(it.data!! as MutableList<MovieEntity>, this@PopularFragment)
                        binding.rvMovies.adapter = popularMovieItemAdapter
                    }

                }
                ResourceStatus.ERROR -> {
                    Log.e("fetchPopularMovies", "Failure: ${it.message} ")
                    Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvMovies.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount - 1 && !loading) {
                    binding.rvMovies.post{
                        loading = true
                        page++
                        Log.e("page", page.toString())
                        moreMovies()
                    }
                }
            }
        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun moreMovies(){
        lifecycleScope.launch(){
            viewModel.fetchPopularMovies(page).observe(viewLifecycleOwner, Observer {
                when (it.resourceStatus) {
                    ResourceStatus.LOADING -> {
                        Log.e("fetchPopularMovies", "Loading")
                    }
                    ResourceStatus.SUCCESS  -> {
                        Log.e("fetchPopularMovies", "Success data: ${it.data?.size}")
                        Log.e("moreMovies: ", it.data.toString())
                        binding.total.text= it.data?.size.toString()
                            popularMovieItemAdapter.moreMovies(it.data as MutableList<MovieEntity>)
                        popularMovieItemAdapter.notifyDataSetChanged()
                    }
                    ResourceStatus.ERROR -> {
                        Log.e("fetchPopularMovies", "Failure: ${it.message} ")
                        Toast.makeText(requireContext(), "Failure: ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                loading = false
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(movieEntity: MovieEntity) {
        val action = PopularFragmentDirections.actionHomeFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)
    }
}