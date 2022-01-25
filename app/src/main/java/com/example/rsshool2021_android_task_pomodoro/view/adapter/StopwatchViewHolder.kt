package com.example.rsshool2021_android_task_pomodoro.view.adapter

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.view.ui.main.StopwatchListener
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding
import com.example.rsshool2021_android_task_pomodoro.displayTime
import com.example.rsshool2021_android_task_pomodoro.model.Stopwatch

class StopwatchViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: StopwatchListener,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null

    fun bind(stopwatch: Stopwatch) {
        binding.timer.text = stopwatch.getCurrentTimeMs().displayTime()

        if (stopwatch.getIsStarted()) {
            startTimer(stopwatch)
        } else {
            stopTimer(stopwatch)
        }

        binding.startStopButton.isEnabled = !stopwatch.getIsFinished()

        initButtons(stopwatch)
    }

    private fun initButtons(stopwatch: Stopwatch) {
        binding.startStopButton.setOnClickListener {
            if (stopwatch.getIsStarted()) {
                listener.stop(stopwatch.getId(), stopwatch.getCurrentTimeMs(), stopwatch.getRunningTimeMs())
            } else {
                listener.start(stopwatch.getId())
            }
        }
        binding.delete.setOnClickListener {
            listener.delete(stopwatch.getId())
        }
    }

    private fun startTimer(stopwatch: Stopwatch) {
        setItemSettings(stopwatch)

        timer?.cancel()
        timer = getCountDownTimer(stopwatch)
        timer?.start()

        binding.dot.isInvisible = false
        (binding.dot.background as? AnimationDrawable)?.start()
    }

    private fun stopTimer(stopwatch: Stopwatch) {
        setItemSettings(stopwatch)

        timer?.cancel()

        binding.dot.isInvisible = true
        (binding.dot.background as? AnimationDrawable)?.stop()
    }

    private fun setItemSettings(stopwatch: Stopwatch) {
        binding.cardView.setCardBackgroundColor(stopwatch.getBackGroundColor())

        if (stopwatch.getIsStarted()) {
            binding.startStopButton.text = context.getString(R.string.stop)
        } else {
            binding.startStopButton.text = context.getString(R.string.start)
        }
    }

    private fun setFinishedStyle(stopwatch: Stopwatch) {
        if (stopwatch.getIsFinished()) {
            with(binding) {
                startStopButton.isEnabled = false
                startStopButton.text = context.getString(R.string.start)
                stopwatch.setBackGroundColor(ContextCompat.getColor(context, R.color.purple_200))
                cardView.setCardBackgroundColor(stopwatch.getBackGroundColor())
                dot.isInvisible = true
                (dot.background as? AnimationDrawable)?.stop()
            }
        }
    }

    private fun getCountDownTimer(stopwatch: Stopwatch): CountDownTimer {
        return object : CountDownTimer(stopwatch.getCurrentTimeMs(), UNIT_TEN_MS) {

            var currentMs = stopwatch.getCurrentTimeMs() + stopwatch.getSystemStaticTimeMs()

            override fun onTick(p0: Long) {
                stopwatch.setRunningTimeMs(currentMs - System.currentTimeMillis())
                binding.timer.text = stopwatch.getRunningTimeMs().displayTime()
                if (stopwatch.getRunningTimeMs() <= 0L) {
                    stopwatch.setIsFinished(true)
                    stopwatch.setIsStarted(false)
                    setFinishedStyle(stopwatch)
                }
            }

            override fun onFinish() {
                stopwatch.setIsFinished(true)
                stopwatch.setIsStarted(false)
                setFinishedStyle(stopwatch)
            }
        }
    }

    private companion object {
        private const val UNIT_TEN_MS = 100L
    }
}