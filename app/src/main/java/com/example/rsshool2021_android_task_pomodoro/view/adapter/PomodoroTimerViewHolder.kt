package com.example.rsshool2021_android_task_pomodoro.view.adapter

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.view.ui.main.PomodoroTimerListener
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding
import com.example.rsshool2021_android_task_pomodoro.view.ui.main.displayTime
import com.example.rsshool2021_android_task_pomodoro.model.PomodoroTimer

class PomodoroTimerViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: PomodoroTimerListener,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null
    private var purpule = 0
    private var wite = 0

    fun bind(pomodoroTimer: PomodoroTimer) {
        binding.timer.text = pomodoroTimer.getCurrentTimeMs().displayTime()
        purpule = ContextCompat.getColor(context, R.color.purple_200)
        wite = ContextCompat.getColor(context, R.color.white)

        if (pomodoroTimer.getIsStarted()) {
            startTimer(pomodoroTimer)
        } else {
            stopTimer(pomodoroTimer)
        }

        binding.startStopButton.isEnabled = !pomodoroTimer.getIsFinished()

        initButtons(pomodoroTimer)
    }

    private fun initButtons(pomodoroTimer: PomodoroTimer) {
        binding.startStopButton.setOnClickListener {
            if (pomodoroTimer.getIsStarted()) {
                listener.stop(pomodoroTimer.getId(), pomodoroTimer.getCurrentTimeMs())
            } else {
                listener.start(pomodoroTimer.getId(), pomodoroTimer.getCurrentTimeMs())
            }
        }
        binding.delete.setOnClickListener {
            listener.delete(pomodoroTimer.getId())
        }
    }

    private fun startTimer(pomodoroTimer: PomodoroTimer) {
        setItemStyle(pomodoroTimer)

        timer?.cancel()
        timer = getCountDownTimer(pomodoroTimer)
        timer?.start()

        binding.dot.isInvisible = false
        (binding.dot.background as? AnimationDrawable)?.start()
    }

    private fun stopTimer(pomodoroTimer: PomodoroTimer) {
        setItemStyle(pomodoroTimer)

        timer?.cancel()

        binding.dot.isInvisible = true
        (binding.dot.background as? AnimationDrawable)?.stop()
    }

    private fun setItemStyle(pomodoroTimer: PomodoroTimer) {
        if (!pomodoroTimer.getIsFinished()) {
            binding.cardView.setCardBackgroundColor(wite)
        } else binding.cardView.setCardBackgroundColor(purpule)

        if (pomodoroTimer.getIsStarted()) {
            binding.startStopButton.text = context.getString(R.string.stop)
        } else {
            binding.startStopButton.text = context.getString(R.string.start)
        }
    }

    private fun setFinishedItemStyle(pomodoroTimer: PomodoroTimer) {
        if (pomodoroTimer.getIsFinished()) {
            with(binding) {
                startStopButton.isEnabled = false
                startStopButton.text = context.getString(R.string.start)
                cardView.setCardBackgroundColor(purpule)
                dot.isInvisible = true
                (dot.background as? AnimationDrawable)?.stop()
            }
        }
    }

    private fun getCountDownTimer(pomodoroTimer: PomodoroTimer): CountDownTimer {
        return object : CountDownTimer(pomodoroTimer.getCurrentTimeMs(), UNIT_TEN_MS) {

            override fun onTick(p0: Long) {
                if (pomodoroTimer.getStartedTimeMs() <= 0L) {
                    pomodoroTimer.setIsFinished(true)
                    pomodoroTimer.setIsStarted(false)
                    setFinishedItemStyle(pomodoroTimer)
                }
                binding.timer.text = pomodoroTimer.getCurrentTimeMs().displayTime()
                pomodoroTimer.setCurrentTimeMs(pomodoroTimer.getRunningTimeMs() - System.currentTimeMillis())
                pomodoroTimer.setStartedTimeMs(pomodoroTimer.getCurrentTimeMs())

            }

            override fun onFinish() {
                pomodoroTimer.setIsFinished(true)
                pomodoroTimer.setIsStarted(false)
                setFinishedItemStyle(pomodoroTimer)
            }
        }
    }

    private companion object {
        private const val UNIT_TEN_MS = 100L
    }
}