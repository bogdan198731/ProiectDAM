package com.example.proiectdam

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {



    lateinit var runnable: Runnable
    var i:Int = 0
    var shufle:Boolean = false
    var maxMelodi:Int = 5
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaMelodi: MutableList<Any> = mutableListOf<Any>()
        listaMelodi.add(MediaPlayer.create(this,R.raw.music))
        listaMelodi.add(MediaPlayer.create(this,R.raw.test))
        listaMelodi.add(MediaPlayer.create(this,R.raw.linkin_lark_in_the_end_mellen_gi_tommee_profi))
        listaMelodi.add(MediaPlayer.create(this,R.raw.gustavo_santaolalla_babel_otnicka_remix_))
        listaMelodi.add(MediaPlayer.create(this,R.raw.okean_eezy_obijmy_dabro_remix_la_calin__the))

        var mediaplayer:MediaPlayer = listaMelodi[i] as MediaPlayer
        nume_piesa.setText("nume Piesa " + (i+1))

        seekbar.progress = 0
        seekbar.max=mediaplayer.duration


        skip_prev_btn.setOnClickListener {
            mediaplayer.pause()
            i = i-1
            if(i < 0)
                i = maxMelodi - 1
            mediaplayer = listaMelodi[i] as MediaPlayer
            initialize(mediaplayer)
        }

        skip_next_btn.setOnClickListener {
            mediaplayer.pause()
            if (!shufle) {
                i = i + 1
                if (i > maxMelodi - 1)
                    i = 0

            }
            else
            {
               i = Random.nextInt(0, maxMelodi)
            }
            mediaplayer = listaMelodi[i] as MediaPlayer
            initialize(mediaplayer)
        }



        play_btn.setOnClickListener {
            if (!mediaplayer.isPlaying) {
                mediaplayer.start()
                play_btn.setImageResource(R.drawable.ic_baseline_pause_24)
            }
            else {
                mediaplayer.pause()
                play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)


            }
        }

        shufle_btn.setOnClickListener {
            if(shufle){
                shufle = false
                shufle_btn.setImageResource(R.drawable.baseline_shuffle_24)
            }
            else {
                shufle = true
                shufle_btn.setImageResource(R.drawable.baseline_shuffle_on_24)
            }
        }


        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                {
                    mediaplayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekbar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
          }
        handler.postDelayed(runnable, 1000)

        mediaplayer.setOnCompletionListener {
 //           play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            if (!shufle) {
                i = i + 1
                if (i > maxMelodi - 1)
                    i = 0

            }
            else
            {
                i = Random.nextInt(0, maxMelodi)
            }
            mediaplayer = listaMelodi[i] as MediaPlayer
            initialize(mediaplayer)

        }



    }

    private fun initialize(mediaplayer:MediaPlayer) {
        nume_piesa.setText("nume Piesa " + (i+1))
        mediaplayer.start()
        seekbar.progress = 0
        seekbar.max=mediaplayer.duration
    }


}

