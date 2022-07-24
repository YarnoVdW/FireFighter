package com.icapps.yarno.firefighter

import android.annotation.SuppressLint
import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FireFighterViewModel(application: Application) : AndroidViewModel(application) {


    // TODO: countdouwn when pressing start, start button disappears
    // TODO: flame has to go back to begin position!!!!!!!!!!!
    // TODO: multiple flames
    // TODO: EXPANSION multiple levels?
    // TODO: app icon

    //for first launch todo
    // TODO: disappearing start button
    // TODO: flame has to go back to center?/?/ kinda



    private var _gameStarted = MutableLiveData(true)
    val gameStarted: LiveData<Boolean>
        get() = _gameStarted

    private var _gameFinished = MutableLiveData(false)
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished


    lateinit var countDownTimer: CountDownTimer
    private val initialCountDownTimerInMillis = 10000L
    private val countDownIntervalInMillis = 1000L

    var _timeLeftOnTimer = MutableLiveData<Int>()
    val timeLeftOnTimer: LiveData<Int>
        get() = _timeLeftOnTimer

    var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var scoreIncrementer = 0

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    fun restoreGame() {
        _timeLeftOnTimer.value?.let { initCountDownTimer(it.toLong()) }
        countDownTimer.start()

    }

    fun resetGame() {
        initCountDownTimer(initialCountDownTimerInMillis)
        _gameStarted.postValue(true)
        _score.value = 0
        scoreIncrementer = 0

    }

    private fun initCountDownTimer(timerLeft: Long = initialCountDownTimerInMillis) {

        countDownTimer = object : CountDownTimer(timerLeft, countDownIntervalInMillis) {
            override fun onTick(p0: Long) {
                _timeLeftOnTimer.postValue((p0 / 1000).toInt())
            }

            override fun onFinish() {
                _gameFinished.postValue(true)
                _gameStarted.postValue(false)
                endGame()
            }

        }
    }

    fun startGame() {
        _gameStarted.postValue(false)
        countDownTimer.start()
        _score.value = 0

    }

    fun endGame() {
        Toast.makeText(
            context,
            "Times up! You extinguished ${_score.value} flames!",
            Toast.LENGTH_LONG
        ).show()
        resetGame()

    }

    fun flameIsClicked() {
        scoreIncrementer++
        _score.value = (scoreIncrementer)
    }
}