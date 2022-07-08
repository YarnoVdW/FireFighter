package com.icapps.yarno.firefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainFireFightActivity : AppCompatActivity() {
    private lateinit var timerView: TextView
    private lateinit var scoreView: TextView
    private lateinit var fighterButton: Button

    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fire_fight)

        timerView = findViewById(R.id.timer_id)
        scoreView = findViewById(R.id.score_id)
        fighterButton = findViewById(R.id.btn_fighter)


    }
}