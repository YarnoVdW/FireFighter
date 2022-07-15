package com.icapps.yarno.firefighter


import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icapps.yarno.firefighter.databinding.ActivityMainFireFightBinding


class MainFireFightActivity : AppCompatActivity() {
    private lateinit var animation: Animation
    private lateinit var flameImg: ImageView
    private lateinit var startButton: Button

    //initialize view model, not like in java..
    private val fireFighterViewModel by lazy {
        ViewModelProvider(this)[FireFighterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainFireFightBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_fire_fight)


        binding.lifecycleOwner = this
        binding.fireFighterViewModel = fireFighterViewModel
        flameImg = findViewById(R.id.fire_main_id)
        startButton = findViewById(R.id.btn_fighter)

        animation = AnimationUtils.loadAnimation(this, R.anim.dissapear)


        // fix this, button has to disappear
        startButton.setOnClickListener {
            startButton.visibility = View.GONE
        }


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)


        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        Log.d("width", width.toString())

        // do something when pressing flame
        flameImg.setOnClickListener {
            val randomIntX = (0..width).random()
            val randomIntY = (0..height).random()

            flameImg.startAnimation(animation)
            flameImg.animate().x(randomIntX.toFloat() / 100)
                .y(randomIntY.toFloat() / 100) // window manager en guidelines!
            fireFighterViewModel.flameIsClicked()


        }
        // restore the imageview's state. set axis back to normal
        if (fireFighterViewModel.endGame()) {
            if (savedInstanceState != null) {
                flameImg.animate().x(width.toFloat() / 2).y(height.toFloat() / 2)
            }

        }



        if (savedInstanceState != null) fireFighterViewModel.restoreGame()
        else fireFighterViewModel.resetGame()

    }

}