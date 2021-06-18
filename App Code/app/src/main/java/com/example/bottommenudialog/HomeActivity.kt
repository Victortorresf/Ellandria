package com.example.bottommenudialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home_activity.*


class HomeActivity : AppCompatActivity() {

    private val list = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)


        list.add(Item("Dash", "/Dash.mp3"))
        list.add(Item("Jump", "/Jump.mp3"))
        list.add(Item("Collect Trophy", "/Trophy.mp3"))
        list.add(Item("Death", "/Death.mp3"))
        list.add(Item("Checkpoint", "/Checkpoint.mp3"))
        list.add(Item("Footsteps", "/Footsteps.mp3"))


        val user = intent.getStringExtra("Username")
        val record = Record(list, user)
        val home = HomeFrag()
        changeFragment(home)

        botton_nav.setOnNavigationItemSelectedListener {
            item ->when(item.itemId){
                R.id.ic_record ->  changeFragment(record)
                R.id.ic_home -> changeFragment(home)
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