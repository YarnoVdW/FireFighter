package com.icapps.yarno.firefighter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class MainScreenActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var animation: Animation
    private lateinit var start_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen_layout)
        setTitle("Firefighter")


        imageView = findViewById(R.id.fire_main_id)
        animation = AnimationUtils.loadAnimation(this, R.anim.start_screen_scaling)
        imageView.startAnimation(animation)
        start_button = findViewById(R.id.start_btn)
        start_button.setOnClickListener {
            val intent = Intent(this,MainFireFightActivity::class.java )
            startActivity(intent)
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if(item.itemId == R.id.menu_abt) {
            showAbout()
        }else if(item.itemId == R.id.htp) {
            howToPlay()
        }
        return true
    }

    private fun showAbout() {
        val about = "Firefighter is still in development\nMade with love\nby - Yakke <3"
        AlertDialog.Builder(this)
            .setTitle("About")
            .setMessage(about)
            .create()
            .show()
    }
    private fun howToPlay(){
        val htp = "You play the game by tapping start. A timer will start counting down.\nThe purpose is to tap as many fires as you can"
        AlertDialog.Builder(this)
            .setTitle("How to play?")
            .setMessage(htp)
            .create().show()
    }



}