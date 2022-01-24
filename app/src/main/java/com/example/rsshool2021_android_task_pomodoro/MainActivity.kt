package com.example.rsshool2021_android_task_pomodoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private var nextId = 0
    private var startTime = 0L
    private val stopwatchAdapter = StopwatchAdapter(this)
    private val stopwatches = mutableListOf<Stopwatch>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    false))
                stopwatchAdapter.submitList(stopwatches.toList())
            }
        }
    }

    private fun getStartedTime(): Long {
        return (binding.timeToSet.text.toString().toLongOrNull()?.times(1000) ?: 0) * 60
    }

    private fun getToast() {
        val text = "Введите время"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    override fun start(id: Int) {
        changeStopwatch(id, null, System.currentTimeMillis(), 0L, true)
    }

    override fun stop(id: Int, displayTimeMs: Long?, runningTimeMs: Long) {
        changeStopwatch(id, displayTimeMs, null, runningTimeMs, false)
    }

    override fun delete(id: Int) {
        stopwatches.remove(stopwatches.find { it.id == id })
        stopwatchAdapter.submitList(stopwatches.toList())
    }

    private fun changeStopwatch(
        id: Int,
        displayTimeMs: Long?,
        systemStaticTimeMs: Long?,
        runningTimeMs: Long,
        isStarted: Boolean,
    ) {
        stopwatches.forEach {
            if (it.id == id && runningTimeMs == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        displayTimeMs ?: it.displayTimeMs,
                        systemStaticTimeMs ?: it.systemStaticTimeMs,
                        runningTimeMs,
                        isStarted)
            } else if (it.id == id) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        runningTimeMs,
                        systemStaticTimeMs ?: it.systemStaticTimeMs,
                        0L,
                        isStarted)
            } else if (it.id != id && it.runningTimeMs == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        it.displayTimeMs,
                        it.systemStaticTimeMs,
                        it.runningTimeMs,
                        false)
            } else {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        it.runningTimeMs,
                        it.systemStaticTimeMs,
                        0L,
                        false)
            }
        }
        stopwatchAdapter.submitList(stopwatches.toList())
    }
}