package com.example.rsshool2021_android_task_pomodoro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding

class StopwatchAdapter(
    private val listener: StopwatchListener,
) :
    androidx.recyclerview.widget.ListAdapter<Stopwatch, StopwatchViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return StopwatchViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<Stopwatch>() {
            override fun areItemsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.currentMs == newItem.currentMs &&
                        oldItem.isStarted == newItem.isStarted
            }

        }
    }
}