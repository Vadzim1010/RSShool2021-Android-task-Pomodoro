package com.example.rsshool2021_android_task_pomodoro.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.rsshool2021_android_task_pomodoro.R

class CircleClockCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defaultAttrs: Int = 0,
) : View(context, attrs, defaultAttrs) {


    private var periodMs = 0L
    private var currentMs = 0L
    private var color = 0
    private var style = FILL
    private val paint = Paint()

    init {
        if (attrs != null) {
            val styledAttrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CircleClockCustomView,
                defaultAttrs,
                0
            )

            color = styledAttrs.getColor(R.styleable.CircleClockCustomView_custom_color, Color.RED)
            style = styledAttrs.getInt(R.styleable.CircleClockCustomView_custom_style, FILL)
            styledAttrs.recycle()
        }

        paint.color = color
        paint.style = if (style == FILL) Paint.Style.FILL else Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (periodMs == 0L || currentMs == 0L) return

        if (periodMs == currentMs) {
            canvas.drawCircle(width / 2F, height / 2F, width / 2F, paint)
        } else {

            val startAngle = (((currentMs % periodMs).toFloat() / periodMs) * 360)

            canvas.drawArc(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                -90f,
                startAngle,
                true,
                paint
            )
        }
    }

    fun setCurrent(current: Long) {
        currentMs = current
        invalidate()
    }

    fun setPeriod(period: Long) {
        periodMs = period
    }

    private companion object {
        private const val FILL = 0
    }
}