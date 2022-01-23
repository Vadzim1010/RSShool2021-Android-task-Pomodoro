package com.example.rsshool2021_android_task_pomodoro

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding
import kotlinx.coroutines.*

class StopwatchViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: StopwatchListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var job: Job? = null

    fun bind(stopwatch: Stopwatch) {
        binding.timer.text = stopwatch.currentMs.displayTime()
        initButtons(stopwatch)

        if (stopwatch.isStarted) {
            startTimer(stopwatch)
        } else {
            stopTimer()
        }
    }

    private fun initButtons(stopwatch: Stopwatch) {
        binding.startStopButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop(stopwatch.id, stopwatch.currentMs)
            } else {
                listener.start(stopwatch.id)
            }
        }
        binding.delete.setOnClickListener {
            listener.delete(stopwatch.id)
        }
    }

    private fun startTimer(stopwatch: Stopwatch) {
        binding.startStopButton.text = "START"
        job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                stopwatch.currentMs -= INTERVAL
                binding.timer.text = stopwatch.currentMs.displayTime()
                delay(INTERVAL)
            }
        }
    }

    private fun stopTimer() {
        binding.startStopButton.text = "STOP"
        job?.cancel()
    }
}