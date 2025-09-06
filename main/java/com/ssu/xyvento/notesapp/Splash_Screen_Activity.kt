package com.ssu.xyvento.notesapp

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.ssu.xyvento.notesapp.databinding.ActivitySplashScreenBinding

class Splash_Screen_Activity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomanimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)


        val splashimage = findViewById<ImageView>(R.id.imageView2)
        val Companyname = findViewById<TextView>(R.id.companyname)
        val Slogan = findViewById<TextView>(R.id.textView7)
        val Collab = findViewById<TextView>(R.id.footertext)

        splashimage.startAnimation(topAnimation)
        Companyname.startAnimation(topAnimation)
        Slogan.startAnimation(bottomanimation)
        Collab.startAnimation(bottomanimation)


        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, Login_Page_Activity::class.java)
            startActivity(intent)
            finish()
        },1800)


    }
}