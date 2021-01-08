package com.example.bottommenudialog

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.IOException

class Record : Fragment(R.layout.fragment_record) {

    private val list = ArrayList<Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

     private var state: Boolean = false
    private var record: MediaRecorder? = null
    private var recordingStopped: Boolean = false
    private var adaptor:Adapter?=null

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list.add(Item("Dash", Environment.getExternalStorageDirectory().absolutePath + "/Dash.mp3"))
        list.add(Item("Jump", Environment.getExternalStorageDirectory().absolutePath + "/Jump.mp3"))
        list.add(Item("Collect Trophy", Environment.getExternalStorageDirectory().absolutePath + "/Trophy.mp3"))

        adaptor = Adapter(list, activity)
        recycler.adapter = adaptor

        var path = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"


        recycler.setOnItemClickListener { _, _, _, list ->
            path = list.toString()
        }

        record = MediaRecorder()

        record?.setAudioSource(MediaRecorder.AudioSource.MIC)
        record?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        record?.setOutputFile(path)
        record?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)


        view.findViewById<Button>(R.id.record_button)?.setOnClickListener {

            startRecording()
            record_time.start()
            record_time.base = SystemClock.elapsedRealtime()
        }

        view.findViewById<Button>(R.id.stop_button)?.setOnClickListener{
            record_time.stop()
            stopRecording()
        }

        view.findViewById<Button>(R.id.pause_button)?.setOnClickListener {
            pauseRecording()
        }
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

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        if(state) {
            if(!recordingStopped){
                Toast.makeText(activity,"Stopped!", Toast.LENGTH_SHORT).show()
                record?.pause()
                recordingStopped = true
                pause_button.text = "Resume"
            }else{
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        Toast.makeText(activity,"Resume!", Toast.LENGTH_SHORT).show()
        record?.resume()
        pause_button.text = "Pause"
        recordingStopped = false
    }

    private fun stopRecording(){
        if(state){
            music += 1
            record?.stop()
            record?.release()
            state = false
        }else{
            Toast.makeText(activity, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

}
