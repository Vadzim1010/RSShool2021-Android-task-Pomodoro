package com.example.rsshool2021_android_task_pomodoro.features.timer.ui

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding
import com.example.rsshool2021_android_task_pomodoro.eventbus.WorkingTimerEvent
import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer
import com.example.rsshool2021_android_task_pomodoro.utils.TIMER_INTERVAL
import com.example.rsshool2021_android_task_pomodoro.utils.displayTime
import org.greenrobot.eventbus.EventBus

class PomodoroTimerViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: PomodoroTimerListener,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null
    private var red = ContextCompat.getColor(context, R.color.red_200)
    private var white = ContextCompat.getColor(context, R.color.white)

    fun bind(pomodoroTimer: PomodoroTimer) {
        binding.timer.text = pomodoroTimer.getCurrentTimeMs().displayTime()
        binding.startStopButton.isEnabled = !pomodoroTimer.getIsFinished()
        binding.circleClock.setCurrent(pomodoroTimer.getCurrentTimeMs())
        binding.circleClock.setPeriod(pomodoroTimer.getStartedTimeMs())

        if (pomodoroTimer.getIsStarted()) {
            startTimer(pomodoroTimer)
        } else {
            stopTimer(pomodoroTimer)
        }

        if (pomodoroTimer.getIsFinished()) {
            setFinishedItemStyle(pomodoroTimer)
        } else setItemStyle(pomodoroTimer)



        initButtons(pomodoroTimer)
    }

    private fun initButtons(pomodoroTimer: PomodoroTimer) {
        binding.startStopButton.setOnClickListener {
            if (!pomodoroTimer.getIsStarted()) {
                listener.start(pomodoroTimer.getId(), pomodoroTimer.getCurrentTimeMs())
                EventBus.getDefault().post(WorkingTimerEvent(null))
            } else {
                EventBus.getDefault().post(WorkingTimerEvent(null))
                listener.stop(pomodoroTimer.getId(), pomodoroTimer.getCurrentTimeMs())
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

    private fun setItemStyle(pomodoroTimer: PomodoroTimer) = binding.run {
        if (!pomodoroTimer.getIsFinished()) {
            cardView.setCardBackgroundColor(white)
        } else cardView.setCardBackgroundColor(red)

        if (pomodoroTimer.getIsStarted()) {
            startStopButton.text = context.getString(R.string.stop)
        } else startStopButton.text = context.getString(R.string.start)
    }

    private fun setFinishedItemStyle(pomodoroTimer: PomodoroTimer) = binding.apply {
        if (pomodoroTimer.getIsFinished()) {
            startStopButton.isEnabled = false
            startStopButton.text = context.getString(R.string.start)
            binding.timer.text = pomodoroTimer.getStartedTimeMs().displayTime()
            cardView.setCardBackgroundColor(red)
            dot.isInvisible = true
            (dot.background as? AnimationDrawable)?.stop()
        }
    }

    private fun getCountDownTimer(pomodoroTimer: PomodoroTimer): CountDownTimer {
        return object :
            CountDownTimer(pomodoroTimer.getRunningTimeMs() - System.currentTimeMillis(),
                TIMER_INTERVAL) {

            override fun onTick(millisUntilFinished: Long) {
                binding.circleClock.setCurrent(millisUntilFinished)

                pomodoroTimer.setCurrentTimeMs(pomodoroTimer.getRunningTimeMs() - System.currentTimeMillis())
                binding.timer.text = pomodoroTimer.getCurrentTimeMs().displayTime()

                EventBus.getDefault().post(WorkingTimerEvent(pomodoroTimer))
            }

            override fun onFinish() {
                pomodoroTimer.setIsFinished(true)
                pomodoroTimer.setIsStarted(false)
                setFinishedItemStyle(pomodoroTimer)
                binding.timer.text = pomodoroTimer.getStartedTimeMs().displayTime()
                binding.circleClock.setCurrent(0)
            }
        }
    }
}
