package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CountdownViewModel : ViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private var _currentTime by mutableStateOf(0L)
    private var _isPause by mutableStateOf(false)
    private var _percent by mutableStateOf(0f)
    private var mTime: Long = 0

    val currentTime: Long
        get() = _currentTime
    val percent: Float
        get() = _percent
    val isPause: Boolean
        get() = _isPause

    fun startCount(time: Long = 50000) {
        this.mTime = time
        _isPause = false
        countDownTimer?.cancel()

        countDownTimer = obtainCountDownTimer(mTime, onTick = { millisUntilFinished ->
            Log.e("CountDownTimer", "onTick time:$millisUntilFinished")
            _currentTime = millisUntilFinished
            _percent = 1.0f - (1.0f * millisUntilFinished) / mTime
        }, onFinish = {
            Log.e("CountDownTimer", "onFinish--")
            _currentTime = 0
            _percent = 1f
        })

        countDownTimer?.start()
    }

    fun restartCount() {
        val tempTime = _currentTime
        countDownTimer = obtainCountDownTimer(tempTime, onTick = { millisUntilFinished ->
            Log.e("CountDownTimer", "onTick time:$millisUntilFinished")
            _currentTime = millisUntilFinished
            _percent = 1.0f - (1.0f * millisUntilFinished) / mTime
        }, onFinish = {
            Log.e("CountDownTimer", "onFinish--")
            _currentTime = 0
            _percent = 1f
        })
        countDownTimer?.start()
        _isPause = false
    }

    fun resetCount() {
        _currentTime = mTime
        _percent = 0f
        countDownTimer?.cancel()
    }

    fun pauseCount() {
        _isPause = true
        countDownTimer?.cancel()
    }

    fun getFormatTimeString(): String {
        val seconds: Long = _currentTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        fun getFormat(time: Long) =
            when (time) {
                0L -> "00"
                in 1..9 -> "0$time"
                else -> time.toString()
            }


        val time =
            getFormat(hours % 24) + ":" + getFormat(minutes % 60) + ":" + getFormat(
                seconds % 60
            )
        return time
    }


    private fun obtainCountDownTimer(
        time: Long,
        onTick: (millisUntilFinished: Long) -> Unit,
        onFinish: () -> Unit
    ) =
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish()
            }
        }
}