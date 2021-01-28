package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemBinding
import com.udacity.asteroidradar.domainentities.DataTransferAsteroid

class AsteroidListAdapter(val onClickListener: OnClickListener): ListAdapter<DataTransferAsteroid, AsteroidListAdapter.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(asteroid) }
        holder.bind(asteroid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class ViewHolder(private var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dataTransferAsteroid: DataTransferAsteroid){
            binding.asteroid = dataTransferAsteroid
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataTransferAsteroid>() {
        override fun areItemsTheSame(oldItem: DataTransferAsteroid, newItem: DataTransferAsteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataTransferAsteroid, newItem: DataTransferAsteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (dataTransferAsteroid: DataTransferAsteroid) -> Unit){
        fun onClick(dataTransferAsteroid:DataTransferAsteroid) = clickListener(dataTransferAsteroid)
    }
}