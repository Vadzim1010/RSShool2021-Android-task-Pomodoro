package com.example.rsshool2021_android_task_pomodoro.view.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.view.ui.service.ForegroundService
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding
import com.example.rsshool2021_android_task_pomodoro.model.PomodoroTimer
import com.example.rsshool2021_android_task_pomodoro.view.adapter.PomodoroTimerAdapter

class MainActivity : AppCompatActivity(), PomodoroTimerListener {

    private var nextId = 0
    private var color = 0
    private var runningTime = 0L
    private val stopwatches = mutableListOf<PomodoroTimer>()
    private val stopwatchAdapter = PomodoroTimerAdapter(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, ForegroundService::class.java)
        color = ContextCompat.getColor(this, R.color.white)

        startService(intent)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        binding.addTimerButton.setOnClickListener {
            if (getStartedTime() == 0L) {
                getToast()
            } else {
                runningTime = System.currentTimeMillis() + getStartedTime()
                stopwatches.add(PomodoroTimer(
                    nextId++,
                    getStartedTime(),
                    runningTime,
                    0L,
                    isStarted = false,
                    isFinished = false))
                stopwatchAdapter.submitList(stopwatches.toList())
            }
        }
    }

    private fun getStartedTime(): Long {
        return (binding.timeToSet.text.toString().toLongOrNull()?.times(100) ?: 0) * 60
    }

    private fun getToast() {
        val text = "Введите время"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    override fun start(id: Int, currentTimeMs: Long) {
        runningTime = System.currentTimeMillis() + currentTimeMs
        stopwatches.sort(id, null, runningTime, true)
        stopwatchAdapter.submitList(stopwatches.toList())
    }

    override fun stop(id: Int, currentTimeMs: Long?) {
        stopwatches.sort(id, currentTimeMs, null, false)
        stopwatchAdapter.submitList(stopwatches.toList())
    }

    override fun delete(id: Int) {
        stopwatches.remove(stopwatches.find { it.getId() == id })
        stopwatchAdapter.submitList(stopwatches.toList())
    }
}