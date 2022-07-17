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


    private var _gameStarted = MutableLiveData(true)
    val gameStarted: LiveData<Boolean>
        get() = _gameStarted

    private var _gameFinished = MutableLiveData(false)
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished


    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDownTimerInMillis = 3000L
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
        _gameFinished.postValue(false)
        _score.postValue(0)


    }

    private fun initCountDownTimer(timerLeft: Long = initialCountDownTimerInMillis) {

        countDownTimer = object : CountDownTimer(timerLeft, countDownIntervalInMillis) {
            override fun onTick(p0: Long) {
                _timeLeftOnTimer.postValue((p0 / 1000).toInt())
            }

            override fun onFinish() {
                _gameFinished.postValue(true)
                _gameStarted.postValue(true)
                endGame()
            }
        }
    }

    fun startGame() {
        _gameStarted.postValue(false)
        _gameFinished.postValue(true)
        countDownTimer.start()
        _score.postValue(0)

    }

    fun endGame() {
        _gameFinished.postValue(true)

        Toast.makeText(
            context,
            "Times up! You extinguished ${_score.value} flames!",
            Toast.LENGTH_LONG
        ).show()
        resetGame()

    }

    fun flameIsClicked() {
        scoreIncrementer++
        _score.postValue(scoreIncrementer)
    }
}