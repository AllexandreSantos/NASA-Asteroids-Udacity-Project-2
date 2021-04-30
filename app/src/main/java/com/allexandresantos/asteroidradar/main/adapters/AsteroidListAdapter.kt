package com.allexandresantos.asteroidradar.main.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.allexandresantos.asteroidradar.databinding.ListItemBinding
import com.allexandresantos.asteroidradar.domainentities.Asteroid

class AsteroidListAdapter(val onClickListener: OnClickListener): ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(asteroid) }
        holder.bind(asteroid)
        holder.positionTest(position)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(private var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dataTransferAsteroid: Asteroid){
            binding.asteroid = dataTransferAsteroid
            binding.executePendingBindings()
        }
        fun positionTest(position: Int){
            if (position == 2 ){
                binding.itemBackground.setBackgroundColor(Color.GREEN)
                binding.itemBackground.maxHeight = 0
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (dataTransferAsteroid: Asteroid) -> Unit){
        fun onClick(dataTransferAsteroid:Asteroid) = clickListener(dataTransferAsteroid)
    }
}