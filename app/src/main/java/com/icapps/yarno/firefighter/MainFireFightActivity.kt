package com.icapps.yarno.firefighter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.icapps.yarno.firefighter.databinding.ActivityMainFireFightBinding

class MainFireFightActivity : AppCompatActivity() {

    private val fireFighterViewModel by lazy {
         ViewModelProvider(this)[FireFighterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainFireFightBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_fire_fight)

        fireFighterViewModel.timeLeftOnTimer.observe(this) {
            Log.d("Fire", "timeleft: $it")
        }

        binding.lifecycleOwner = this
        binding.fireFighterViewModel = fireFighterViewModel

        if (savedInstanceState != null) fireFighterViewModel.restoreGame()
        else fireFighterViewModel.resetGame()

    }
}