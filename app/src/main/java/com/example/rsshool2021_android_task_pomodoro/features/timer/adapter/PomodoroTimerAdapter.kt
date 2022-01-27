package com.example.rsshool2021_android_task_pomodoro.features.timer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.rsshool2021_android_task_pomodoro.features.timer.ui.PomodoroTimerListener
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding
import com.example.rsshool2021_android_task_pomodoro.features.timer.ui.PomodoroTimerViewHolder
import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer

class PomodoroTimerAdapter(
    private val listener: PomodoroTimerListener,
) :
    androidx.recyclerview.widget.ListAdapter<PomodoroTimer, PomodoroTimerViewHolder>(itemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PomodoroTimerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return PomodoroTimerViewHolder(binding, listener, binding.root.context)
    }

    override fun onBindViewHolder(holder: PomodoroTimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<PomodoroTimer>() {
            override fun areItemsTheSame(oldItem: PomodoroTimer, newItem: PomodoroTimer): Boolean {
                return oldItem.getId() == newItem.getId()
            }

            override fun areContentsTheSame(oldItem: PomodoroTimer, newItem: PomodoroTimer): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: PomodoroTimer, newItem: PomodoroTimer) = Any()
        }
    }
}