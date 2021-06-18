package com.example.bottommenudialog.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottommenudialog.HomeActivity
import com.example.bottommenudialog.R
import com.example.bottommenudialog.Routes
import kotlinx.android.synthetic.main.login_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)


        login.setOnClickListener {
            val user = User(username.text.toString(), password.text.toString())
            login(user)
        }

        txt_forgot_password.setOnClickListener(){
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    fun login(user: User) {
        Routes().login(user).enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    toast("Login Successful")
                    println(response)
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("Username", username.text.toString())
                    startActivity(intent)
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    toast("Wrong Password or Username")
                }
            }
        )
    }

    private fun toast(string: String) {
        val applicationContext = this@LoginActivity
        Toast.makeText(
            applicationContext,
            string,
            Toast.LENGTH_LONG
        ).show()
    }
}