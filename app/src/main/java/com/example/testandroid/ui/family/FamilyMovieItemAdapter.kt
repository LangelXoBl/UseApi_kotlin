package com.example.testandroid.ui.family

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testandroid.data.entities.MovieEntity
import com.example.testandroid.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class FamilyMovieItemAdapter(
   private val moviesList: List<MovieEntity>,
   private val  itemClickListener: FamilyFragment
): RecyclerView.Adapter<FamilyMovieItemAdapter.FamilyViewHolder>(){

    interface OnMovieClickListener{
        fun  OnMovieClick(movieEntity: MovieEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  FamilyViewHolder(binding)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: FamilyViewHolder, position: Int) {
        with(holder){
            with(moviesList[position]){
                binding.titleMovieText.text = title
                binding.overviewMovieText.text = overview
                binding.percentageMovieText.text = voteAverage.toString()
                binding.releaseMovieText.text = releaseDate
                Picasso.get().load("https://image.tmdb.org/t/p/w500"+(posterPath ?:"")).into(binding.posterMovieImage)

                holder.itemView.setOnClickListener {
                    itemClickListener.OnMovieClick(this)
                }
            }
        }
    }

    inner class FamilyViewHolder(val binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root)
}