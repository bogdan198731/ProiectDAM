package com.example.proiectdam

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.internet.*

class Internet : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.internet)




        var mediaplayer : MediaPlayer = MediaPlayer()

        get_Piesa.setOnClickListener{

            var numePiesa: String = nume_piesa.getText().toString()
            if(numePiesa.length > 5 ) {
                mediaplayer.setDataSource(numePiesa)
                play_music(mediaplayer)
            }
        }


    }
}

public fun play_music( mediaplayer: MediaPlayer){
    mediaplayer.prepare()
    mediaplayer.start()
}