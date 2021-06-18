package com.example.bottommenudialog

import androidx.fragment.app.Fragment


class MediaPlayer(private val list: ArrayList<Item>) : Fragment(R.layout.media_player) {
/*
    private var mp: MediaPlayer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var path = Uri.parse(Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3")
        controlSound(path)

        list_audio.setOnItemClickListener{ _, _, id, _ ->
            val song = list[id].effect
            path = Uri.parse(Environment.getExternalStorageDirectory().absolutePath + song)
            controlSound(path)
        }

    }

    private fun controlSound(uri: Uri){
        bt_play.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(activity, uri)
                Log.d("MediaPlayer", "ID: ${mp!!.audioSessionId}")


            }
            mp?.start()
            Log.d("MediaPlayer", "Duration: ${mp!!.duration / 1000} seconds")
        }

        stop_button.setOnClickListener {
            if (mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        bt_upload.setOnClickListener {
            UploadUtility(requireActivity()).uploadFile(uri)
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
    }*/
}
