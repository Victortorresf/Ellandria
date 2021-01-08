package com.example.bottommenudialog

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.media_player.*


class MediaPlayer : Fragment(R.layout.media_player) {

    //private val recorded_audio = ArrayList<Item>()
    private var mp: MediaPlayer? = null
    //private var currentSong = mutableListOf(parse("/Armazenamento interno/Beat It.mp3"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.media_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = ("/Armazenamento interno/Ellandria/Beat It.mp3")
        controlSound(path)
    }

    private fun controlSound(uri: String){
        fab_play.setOnClickListener{

            if (mp == null){
                mp = MediaPlayer.create(activity, Uri.parse(uri))
                //Error on line 46 - audioSessionId
                //Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")

               // initialiseSeekBar()
            }
            mp?.start()
            //Log.d("MediaPlayer", "Duration: ${mp!!.duration / 1000} seconds")
        }

        fab_pause.setOnClickListener{
            if (mp !== null) mp?.pause()
            //Log.d("MediaPlayer", "Paused at: ${mp!!.currentPosition / 1000} seconds")
        }

        fab_stop.setOnClickListener {
            if (mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        /*seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })*/
    }

    /*private fun initialiseSeekBar() {
        seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekBar.progress = 0
                }
            }
        }, 0)
    }*/
}
