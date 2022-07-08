package com.icapps.yarno.firefighter

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FireFighterViewModel(application: Application) : AndroidViewModel(application) {

    var gameStarted = false


    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDownTimerInMillis = 10000L
    private val countDownIntervalInMillis = 1000L

    private var _timeLeftOnTimer = MutableLiveData<Int>()
    val timeLeftOnTimer: LiveData<Int>
        get() = _timeLeftOnTimer


    private var timeLeft = initialCountDownTimerInMillis

    fun restoreGame() {
        initCountDownTimer(timeLeft)
    }

    fun resetGame() {
        initCountDownTimer(initialCountDownTimerInMillis)
        gameStarted = false
    }

    private fun initCountDownTimer(timerLeft: Long = initialCountDownTimerInMillis) {

        countDownTimer = object : CountDownTimer(timerLeft, countDownIntervalInMillis) {
            override fun onTick(p0: Long) {

                _timeLeftOnTimer.postValue((p0 / 1000).toInt())
            }

            override fun onFinish() {
                endGame()
                gameStarted = false
            }
        }
    }

    fun startGame() {
        gameStarted = true
        countDownTimer.start()

    }

    private fun endGame() {

    }
}