package com.example.testandroid.ui.Family

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
import com.example.testandroid.databinding.FragmentFamilyBinding
import kotlin.math.log

class FamilyFragment : Fragment(), FamilyMovieItemAdapter.OnMovieClickListener {
    private var _binding: FragmentFamilyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FamilyViewModel by navGraphViewModels(R.id.nav_graph){
        defaultViewModelProviderFactory
    }

    private lateinit var familyMovieItemAdapter: FamilyMovieItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFamilyBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.layoutManager = LinearLayoutManager(context)

        viewModel.fetchFamilyMovies.observe(viewLifecycleOwner, Observer {
            when(it.resourceStatus){
                ResourceStatus.LOADING->{
                    Log.e("Fetch Family Movies", "Loading")
                }
                ResourceStatus.SUCCESS->{
                    Log.e("Fetch Family Movies","Success daa: ${it.data?.size}")
                    familyMovieItemAdapter = FamilyMovieItemAdapter(it.data!!,this@FamilyFragment)
                    binding.rvMovies.adapter = familyMovieItemAdapter
                }
                ResourceStatus.ERROR->{
                    Log.e("fetch Family Movies", "Failure: ${it.message}")
                    Toast.makeText(requireContext(),"Failure: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnMovieClick(movieEntity: MovieEntity) {
        val action = FamilyFragmentDirections.actionFamilyFragmentToDetailFragment(movieEntity)
        findNavController().navigate(action)
    }

}