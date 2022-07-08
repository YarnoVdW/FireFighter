package com.icapps.yarno.firefighter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icapps.yarno.firefighter.databinding.ActivityMainFireFightBinding

class MainFireFightActivity : AppCompatActivity() {
    private lateinit var animation: Animation
    private lateinit var flameImg: ImageView
    private lateinit var startButton: Button
    private val fireFighterViewModel by lazy {
        ViewModelProvider(this)[FireFighterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainFireFightBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_fire_fight)



        binding.lifecycleOwner = this
        binding.fireFighterViewModel = fireFighterViewModel
        flameImg = findViewById(R.id.fire_id)
        startButton = findViewById(R.id.btn_fighter)

        animation = AnimationUtils.loadAnimation(this, R.anim.dissapear)


        startButton.setOnClickListener {
            startButton.visibility = View.GONE
        }
        flameImg.setOnClickListener {
            val randomInt = (0..1000).random()
            flameImg.startAnimation(animation)
            flameImg.animate().x(randomInt.toFloat()).y(randomInt.toFloat()) // window manager en guidelines!
            fireFighterViewModel.flameIsClicked()

        }

        if (savedInstanceState != null) fireFighterViewModel.restoreGame()
        else fireFighterViewModel.resetGame()

    }

}