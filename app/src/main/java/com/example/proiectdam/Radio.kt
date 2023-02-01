package com.example.proiectdam

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class Radio : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.radio)


        var mediaplayer :MediaPlayer = MediaPlayer()

        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaplayer.setDataSource("https://www.youtube.com/watch?v=Dl459QlwkdQ")
     //   mediaplayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")

        mediaplayer.prepare()
        mediaplayer.start()
    }
}