package com.example.rsshool2021_android_task_pomodoro

import android.content.Context
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding

class StopwatchViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: StopwatchListener,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null

    fun bind(stopwatch: Stopwatch) {
        binding.timer.text = stopwatch.currentTimeMs.displayTime()

        if (stopwatch.isStarted) {
            startTimer(stopwatch)
        } else {
            stopTimer(stopwatch)
        }

        binding.startStopButton.isEnabled = !stopwatch.isFinished

        initButtons(stopwatch)
    }

    private fun initButtons(stopwatch: Stopwatch) {
        binding.startStopButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop(stopwatch.id, stopwatch.currentTimeMs, stopwatch.runningTimeMs)
            } else {
                listener.start(stopwatch.id)
            }
        }
        binding.delete.setOnClickListener {
            listener.delete(stopwatch.id)
        }
    }

    private fun startTimer(stopwatch: Stopwatch) {
        setItemSettings(stopwatch)
        timer?.cancel()
        timer = getCountDownTimer(stopwatch)
        timer?.start()
    }

    private fun stopTimer(stopwatch: Stopwatch) {
        setItemSettings(stopwatch)
        timer?.cancel()
    }

    private fun setItemSettings(stopwatch: Stopwatch) {
        binding.cardView.setCardBackgroundColor(stopwatch.backGroundColor)

        if (stopwatch.isStarted) {
            binding.startStopButton.text = context.getString(R.string.stop)
        } else {
            binding.startStopButton.text = context.getString(R.string.start)
        }
    }

    private fun setFinishedStyle(stopwatch: Stopwatch) {
        if (stopwatch.isFinished) {
            binding.startStopButton.isEnabled = false
            binding.startStopButton.text = context.getString(R.string.start)
            stopwatch.backGroundColor = ContextCompat.getColor(context, R.color.purple_200)
            binding.cardView.setCardBackgroundColor(stopwatch.backGroundColor)
        }
    }

    private fun getCountDownTimer(stopwatch: Stopwatch): CountDownTimer {
        return object : CountDownTimer(stopwatch.currentTimeMs, UNIT_TEN_MS) {

            var currentMs = stopwatch.currentTimeMs + stopwatch.systemStaticTimeMs

            override fun onTick(p0: Long) {
                stopwatch.runningTimeMs = currentMs - System.currentTimeMillis()
                binding.timer.text = stopwatch.runningTimeMs.displayTime()
                if (stopwatch.runningTimeMs <= 0L) {
                    stopwatch.isFinished = true
                    setFinishedStyle(stopwatch)
                }
            }

            override fun onFinish() {
                stopwatch.isFinished = true
                setFinishedStyle(stopwatch)
            }
        }
    }

    private companion object {
        private const val UNIT_TEN_MS = 100L
    }
}