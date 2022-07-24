package com.icapps.yarno.firefighter


import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icapps.yarno.firefighter.databinding.ActivityMainFireFightBinding
import java.util.*


@Suppress("DEPRECATION")
class MainFireFightActivity : AppCompatActivity() {
    lateinit var animation: Animation
    private lateinit var flameImg: ImageView

    private val displayMetric = DisplayMetrics()

    //initialize view model, not like in java..
    private val fireFighterViewModel by lazy {
        ViewModelProvider(this)[FireFighterViewModel::class.java]
    }

    // static var
    companion object {
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_KEY = "TIME_KEY"
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainFireFightBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_fire_fight)

        binding.lifecycleOwner = this
        binding.fireFighterViewModel = fireFighterViewModel
        flameImg = findViewById(R.id.fire_main_id)

        animation = AnimationUtils.loadAnimation(this, R.anim.dissapear)


        windowManager.defaultDisplay.getMetrics(displayMetric)



        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        Log.d("width", displayMetric.widthPixels.toString())
        Log.d("height", displayMetric.heightPixels.toString())


        flameImg.setOnClickListener { p0 ->
            val random = Random()
            val dx: Float = random.nextFloat() * displayMetric.widthPixels
            val dy: Float = random.nextFloat() * displayMetric.heightPixels
            p0?.animate()?.x(dx/2)?.y(dy/2)?.setDuration(0)?.start()
            fireFighterViewModel.flameIsClicked()
        }


        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        //observe live data and do something when changed
        this.fireFighterViewModel.gameFinished.observe(this, object : Observer,
            androidx.lifecycle.Observer<Boolean> {
            override fun update(p0: Observable?, p1: Any?) {

                }
            override fun onChanged(t: Boolean?) {
                if (fireFighterViewModel.gameFinished.value == true) {
                    //find center of screen

                    flameImg.animate()
                        ?.x(width.toFloat()/2.75.toFloat())?.y((height/4).toFloat())?.setDuration(0)?.start()

                    Log.d("finished", "true")
                    Log.d("width",Resources.getSystem().displayMetrics.widthPixels.toString())
                    Log.d("height", Resources.getSystem().displayMetrics.heightPixels.toString())

                }
            }
        })


        if (savedInstanceState != null) {
            fireFighterViewModel._score.postValue(savedInstanceState.getInt(SCORE_KEY))
            fireFighterViewModel._timeLeftOnTimer.postValue(savedInstanceState.getInt(TIME_KEY))
            fireFighterViewModel.restoreGame()
        }
        else fireFighterViewModel.resetGame()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        fireFighterViewModel.score.value?.let { outState.putInt(SCORE_KEY, it) }
        fireFighterViewModel.timeLeftOnTimer.value?.let { outState.putInt(TIME_KEY, it) }
        fireFighterViewModel.countDownTimer.cancel()
        super.onSaveInstanceState(outState)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_game, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.home_id) {
           val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
            fireFighterViewModel.countDownTimer.cancel()
            fireFighterViewModel.endGame()
            finishActivity(1)
        }
        return true
    }

}