package com.icapps.yarno.firefighter

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FireFighterViewModel(application: Application) : AndroidViewModel(application) {

    // TODO: Launch screen
    // TODO: countdouwn when pressing start, start button disappears
    // TODO: flame has to go back to begin position
    // TODO: multiple flames
    // TODO: EXPANSION multiple levels?
    // TODO: app icon

    private var _gameStarted = MutableLiveData<Boolean>(true)
    val gameStarted: LiveData<Boolean>
        get() = _gameStarted


    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDownTimerInMillis = 10000L
    private val countDownIntervalInMillis = 1000L

    private var _timeLeftOnTimer = MutableLiveData<Int>()
    val timeLeftOnTimer: LiveData<Int>
        get() = _timeLeftOnTimer

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    private var scoreIncrementer = 0
    private val context = getApplication<Application>().applicationContext


    private var timeLeft = initialCountDownTimerInMillis

    fun restoreGame() {
        initCountDownTimer(timeLeft)
    }

    fun resetGame() {
        initCountDownTimer(initialCountDownTimerInMillis)
        _gameStarted.postValue(true)
        _score.value = 0


    }

    private fun initCountDownTimer(timerLeft: Long = initialCountDownTimerInMillis) {

        countDownTimer = object : CountDownTimer(timerLeft, countDownIntervalInMillis) {
            override fun onTick(p0: Long) {

                _timeLeftOnTimer.postValue((p0 / 1000).toInt())
            }

            override fun onFinish() {
                endGame()
                _gameStarted.postValue(true)
            }
        }
    }

    fun startGame() {
        _gameStarted.postValue(false)
        countDownTimer.start()
    }

    fun endGame(): Boolean {
        Toast.makeText(
            context,
            "Times up! You extinguished ${_score.value} flames!",
            Toast.LENGTH_LONG
        ).show()
        resetGame()

        return true
    }

    fun flameIsClicked() {
        scoreIncrementer++
        _score.postValue(scoreIncrementer)
    }
}