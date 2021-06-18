package com.example.bottommenudialog

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.IOException

class Record(private val list: ArrayList<Item>, user: String?) : Fragment(R.layout.fragment_record) {

    private var mp: MediaPlayer? = null
    private var state: Boolean = false
    private var record: MediaRecorder? = null
    private var recordingStopped: Boolean = false
    private var adaptor:Adapter?=null
    val name = user

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        record = MediaRecorder()
        adaptor = Adapter(list, activity)
        grid.adapter = adaptor

        val music = "/recording.mp3"
        var path = Environment.getExternalStorageDirectory().absolutePath + music

        grid.setOnItemClickListener { _, _, id, _ ->
            val song = list[id].effect
            path = Environment.getExternalStorageDirectory().absolutePath + song
        }


        view.findViewById<Button>(R.id.record_button)?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(context as Activity,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context as Activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(context as Activity, permissions,0)
            } else {
                record?.setAudioSource(MediaRecorder.AudioSource.MIC)
                record?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                record?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                record?.setOutputFile(path)
                startRecording()
                record_time.start()
                record_time.base = SystemClock.elapsedRealtime()
            }
        }

        view.findViewById<Button>(R.id.stop_button)?.setOnClickListener{
            stopRecording()
            record_time.stop()
            stopPlayer()
        }

        view.findViewById<Button>(R.id.bt_play)?.setOnClickListener {
            play(Uri.parse(path))
        }

        bt_upload.setOnClickListener {
            if (name != null) {
                UploadUtility(requireActivity(), name).uploadFile(path)
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun startRecording() {
        try {
            record?.prepare()
            record?.start()
            state = true
            Toast.makeText(activity, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun stopRecording(){
        if(state){
            record?.stop()
            record?.reset()
            state = false
        }
    }

    private fun play(uri: Uri){

        if (mp == null){
            mp = MediaPlayer.create(activity, uri)
            Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")
            initialiseSeekBar()
        }
        mp?.start()
        Log.d("MediaPlayer", "Duration: ${mp!!.duration / 1000} seconds")
    }

    private fun stopPlayer(){
        if (mp !== null){
            mp?.stop()
            mp?.reset()
            mp?.release()
            mp = null
        }

    }

    private fun initialiseSeekBar() {
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
    }
}

