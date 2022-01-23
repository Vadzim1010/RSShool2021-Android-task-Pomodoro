package com.example.rsshool2021_android_task_pomodoro

import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding

class StopwatchViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: StopwatchListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(stopwatch: Stopwatch) {
        binding.timer.text = stopwatch.currentMs.displayTime()
        initButtons(stopwatch)
    }

    private fun initButtons(stopwatch: Stopwatch) {
        binding.startStopButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop()
            } else {
                listener.start()
            }
        }
        binding.delete.setOnClickListener {
            listener.delete()
        }
    }
}