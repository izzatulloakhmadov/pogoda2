package com.example.pogoda2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pogoda2.databinding.WeatherItemBinding
import com.example.pogoda2.model.Forecastday

class WeatherAdapter : ListAdapter<Forecastday, WeatherAdapter.MyViewHolder>(diffUtil) {


    class MyViewHolder(val binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root)


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Forecastday>() {
            override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
                return oldItem.day == newItem.day

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            Glide.with(holder.itemView).load("https:" + item.day.condition.icon).into(img)
            txttem.text = item.day.avgtempC.toString() + "°C"
            txtdata.text = item.date

        }
    }
}