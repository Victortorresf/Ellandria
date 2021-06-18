package com.example.bottommenudialog


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.bottommenudialog.login.LoginActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        Handler().postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity:: class.java)
            startActivity(intent)
        }, 2000)
    }
}