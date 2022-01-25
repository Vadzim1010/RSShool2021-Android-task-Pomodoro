package com.example.rsshool2021_android_task_pomodoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private var nextId = 0
    private var startTime = 0L
    private val stopwatchAdapter = StopwatchAdapter(this, this)
    private val stopwatchSorter = StopwatchSorter()
    private val stopwatches = mutableListOf<Stopwatch>()
    private var color = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        color = ContextCompat.getColor(this, R.color.white)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stopwatchAdapter
        }

        startTime = System.currentTimeMillis()

        val intent = Intent(this, ForegroundService::class.java)
        startService(intent)

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
        stopwatches.remove(stopwatches.find { it.id == id })
        stopwatchAdapter.submitList(stopwatches.toList())
    }
}