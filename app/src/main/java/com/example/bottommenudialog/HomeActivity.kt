package com.example.bottommenudialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val record = Record()
        val player = MediaPlayer()
        val home = HomeFrag()

        botton_nav.setOnNavigationItemSelectedListener {
            item ->when(item.itemId){
                R.id.ic_record ->  changeFragment(record)
                R.id.ic_home -> changeFragment(home)
                R.id.ic_player -> changeFragment(player)
            }
            true
        }


    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_cont, fragment)
            commit()
        }
    }
}