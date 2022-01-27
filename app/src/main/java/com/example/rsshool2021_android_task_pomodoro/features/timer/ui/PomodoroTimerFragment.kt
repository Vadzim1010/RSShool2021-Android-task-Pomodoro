package com.example.rsshool2021_android_task_pomodoro.features.timer.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rsshool2021_android_task_pomodoro.databinding.FragmentPomodoroTimerBinding
import com.example.rsshool2021_android_task_pomodoro.features.timer.adapter.PomodoroTimerAdapter
import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer
import com.example.rsshool2021_android_task_pomodoro.utils.sort

class PomodoroTimerFragment : Fragment(), PomodoroTimerListener {

    private var nextId = 0
    private val pomodoroTimers = mutableListOf<PomodoroTimer>()
    private val pomodoroTimerAdapter = PomodoroTimerAdapter(this)
    private var _binding: FragmentPomodoroTimerBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPomodoroTimerBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pomodoroTimerAdapter
        }
    }

    private fun initListeners() = binding.run {
        addTimerButton.setOnClickListener {
            if (getStartedTime() == 0L) {
                getToast()
            } else {
                pomodoroTimers.add(PomodoroTimer(
                    nextId++,
                    getStartedTime(),
                    0L,
                    getStartedTime(),
                    isStarted = false,
                    isFinished = false))
                pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(startMain)
            }
    }

    private fun getStartedTime(): Long {
        return (binding.timeToSet.text.toString().toLongOrNull()?.times(1000) ?: 0) * 60
    }

    private fun getToast() {
        val text = "Введите время"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    override fun start(id: Int, currentTimeMs: Long) {
        val runningTime = System.currentTimeMillis() + currentTimeMs
        pomodoroTimers.sort(id, currentTimeMs, runningTime, true)
        pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
    }

    override fun stop(id: Int, currentTimeMs: Long?) {
        pomodoroTimers.sort(id, currentTimeMs, null, false)
        pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
    }

    override fun delete(id: Int) {
        pomodoroTimers.remove(pomodoroTimers.find { it.getId() == id })
        pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
    }

    override fun onResume() {
        super.onResume()
        pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
    }

    override fun onPause() {
        super.onPause()
        pomodoroTimerAdapter.submitList(pomodoroTimers.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
