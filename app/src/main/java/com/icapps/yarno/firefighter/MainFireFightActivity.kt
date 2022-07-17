package com.icapps.yarno.firefighter


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AbsoluteLayout
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icapps.yarno.firefighter.databinding.ActivityMainFireFightBinding
import java.util.*


class MainFireFightActivity : AppCompatActivity() {
    private lateinit var animation: Animation
    private lateinit var flameImg: ImageView
    private lateinit var startButton: Button
    private val displaymatrics = DisplayMetrics()

    //initialize view model, not like in java..
    private val fireFighterViewModel by lazy {
        ViewModelProvider(this)[FireFighterViewModel::class.java]
    }

    @SuppressLint("ClickableViewAccessibility")
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
        windowManager.defaultDisplay.getMetrics(displaymatrics)

        Log.d("width", displaymatrics.widthPixels.toString())
        Log.d("heigth", displaymatrics.heightPixels.toString())


        flameImg.setOnClickListener { p0 ->
            val R = Random()
            val dx: Float = R.nextFloat() * displaymatrics.widthPixels
            val dy: Float = R.nextFloat() * displaymatrics.heightPixels
            p0?.animate()?.x(dx)?.y(dy)?.setDuration(0)?.start()
            fireFighterViewModel.flameIsClicked()
        }


        if (savedInstanceState != null) fireFighterViewModel.restoreGame()
        else fireFighterViewModel.resetGame()

    }

    override fun onStart() {
        super.onStart()
        if (fireFighterViewModel.gameFinished.value == true) {
            flameImg.animate().x((displaymatrics.widthPixels).toFloat())
                .y(displaymatrics.heightPixels.toFloat()).setDuration(0).start()
            Log.d("finished", "true")


        }
    }

}