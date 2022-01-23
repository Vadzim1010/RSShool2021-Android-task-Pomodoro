package com.example.rsshool2021_android_task_pomodoro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StopwatchListener {

    private var nextId = 0
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

        val intent = Intent(this, ForegroundService::class.java)
        startService(intent)

        binding.addTimerButton.setOnClickListener {
            if (getStartedTime() == 0L) {
                getToast()
            } else {
                stopwatches.add(Stopwatch(nextId++, getStartedTime(), false))
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

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }
}