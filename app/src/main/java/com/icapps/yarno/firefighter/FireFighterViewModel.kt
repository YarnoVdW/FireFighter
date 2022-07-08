package com.icapps.yarno.firefighter

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FireFighterViewModel(application: Application) : AndroidViewModel(application) {


    private var _gameStarted = MutableLiveData<Boolean>(true)
    val gameStarted: LiveData<Boolean>
        get() = _gameStarted


    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDownTimerInMillis = 5000L
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

    private fun endGame() {
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