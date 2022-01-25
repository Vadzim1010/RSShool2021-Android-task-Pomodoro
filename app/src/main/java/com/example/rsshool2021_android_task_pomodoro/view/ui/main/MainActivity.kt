package com.example.rsshool2021_android_task_pomodoro.view.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.view.ui.service.ForegroundService
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.view.data.StopwatchSorter
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding
import com.example.rsshool2021_android_task_pomodoro.model.Stopwatch
import com.example.rsshool2021_android_task_pomodoro.view.adapter.StopwatchAdapter

class MainActivity : AppCompatActivity(), StopwatchListener {

    private var nextId = 0
    private var startTime = 0L
    private var color = 0
    private val stopwatches = mutableListOf<Stopwatch>()
    private val stopwatchAdapter = StopwatchAdapter(this)
    private val stopwatchSorter = StopwatchSorter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, ForegroundService::class.java)
        color = ContextCompat.getColor(this, R.color.white)
        startTime = System.currentTimeMillis()

        startService(intent)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        binding.addTimerButton.setOnClickListener {
            if (getStartedTime() == 0L) {
                getToast()
            } else {
                stopwatches.add(Stopwatch(
                    nextId++,
                    getStartedTime(),
                    0L,
                    0L,
                    isStarted = false,
                    isFinished = false,
                    color))
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

    override fun start(id: Int) {
        stopwatchSorter.sort(stopwatches,
            stopwatchAdapter,
            id,
            null,
            System.currentTimeMillis(),
            0L,
            true)
    }

    override fun stop(id: Int, currentTimeMs: Long?, runningTimeMs: Long) {
        stopwatchSorter.sort(stopwatches,
            stopwatchAdapter,
            id,
            currentTimeMs,
            null,
            runningTimeMs,
            false)
    }

    override fun delete(id: Int) {
        stopwatches.remove(stopwatches.find { it.getId() == id })
        stopwatchAdapter.submitList(stopwatches.toList())
    }
}