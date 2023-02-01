package com.example.proiectdam

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.music_play.*
import java.io.Serializable
import java.util.ArrayList
import kotlin.random.Random

class play_music : AppCompatActivity(), Serializable {


    lateinit var runnable: Runnable
    var i = 0
    lateinit var listaMelodiT: ArrayList<String>


    var shufle:Boolean = false
    var repeat:Boolean = false

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_play)



        val intent = intent
        var listaMelodiI: ArrayList<String>? = intent.getStringArrayListExtra("key")

        if (listaMelodiI != null) {
            listaMelodiT = listaMelodiI
        }

        var listaMelodi = createMediaplayer(listaMelodiI)
        var listaMelodiS = listaMelodi

        var maxMelodi:Int = intent.getIntExtra("key2", 0)
        print(" maxMelodi = " + maxMelodi)

        i = intent.getIntExtra("key3", 0)
        var one = intent.getBooleanExtra("key4", false)
        print("one = " + one)

        println("i in playMusic = " + i)

        var mediaplayer :MediaPlayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer


  //     var mediaplayer: MediaPlayer = MediaPlayer()
 //      mediaplayer.setDataSource("/storage/emulated/0/Download/okean_eezy_obijmy_dabro_remix_la_calin__the.mp3")
 //       mediaplayer.setDataSource(listaMelodiS?.get(1) ?: "/storage/emulated/0/Download/okean_eezy_obijmy_dabro_remix_la_calin__the.mp3")
        mediaplayer.prepare()
        nume_piesa.setText(getName(this.listaMelodiT[i]))

        seekbar.max = mediaplayer.duration
        seekbar.progress = 0

        mediaplayer.start()



        //     val ecran = findViewById(R.id.screen) as LinearLayout
        //      ecran.setBackgroundColor(Color.rgb(rosu, verde, albastru))



        // buton Play/Pause
        play_btn.setOnClickListener {
            if (!mediaplayer.isPlaying) {
                mediaplayer.start()
                seekbar.max = mediaplayer.duration
                play_btn.setImageResource(R.drawable.ic_baseline_pause_24)
                listaMelodi = listaMelodiS

            } else {
                mediaplayer.pause()
                play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }

        // buton melodie precedenta
        skip_prev_btn.setOnClickListener {
            mediaplayer.release()
            if(!repeat) {
                if (!shufle) {
                    i = i - 1
                    if (i < 0)
                        i = maxMelodi

                } else {
                    i = Random.nextInt(0, maxMelodi)

                }
            }
            mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
            initialize(mediaplayer)

        }

        // buton melodie urmatoare
        skip_next_btn.setOnClickListener {
            mediaplayer.release()
            if(!repeat) {
                if (!shufle) {
                    i = i + 1
                    if (i > maxMelodi )
                        i = 0

                } else {
                    i = Random.nextInt(0, maxMelodi)

                }
            }
            mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
            initialize(mediaplayer)
            listaMelodi = listaMelodiS
        }

        // buton shufle
        shufle_btn.setOnClickListener {
            if (shufle) {
                shufle = false
                shufle_btn.setImageResource(R.drawable.baseline_shuffle_24)
            } else {
                shufle = true
                shufle_btn.setImageResource(R.drawable.baseline_shuffle_on_24)
            }
        }

        // buton repeat
        repeat_btn.setOnClickListener {
            if (repeat) {
                repeat = false
                repeat_btn.setImageResource(R.drawable.baseline_repeat_24)
            } else {
                repeat = true
                repeat_btn.setImageResource(R.drawable.baseline_repeat_one_on_24)
            }
        }

        //seekbar
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaplayer.seekTo(progress)
                }

//              Cod adaugat pt a remedia o problema cu mediaplayer.setOnCompletionListener
//            care nu declanseaza intatdeauna la finalul melodiei
                if ((seekbar.progress == (seekbar.max - 1)) && !one) {
                    if (!repeat) {
                        if (!shufle) {
                            i = i + 1
                            if (i > maxMelodi - 1)
                                i = 0

                        } else {
                            i = Random.nextInt(0, maxMelodi)

                        }
                        mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
                        initialize(mediaplayer)
                        listaMelodi = listaMelodiS
                    }
                    else
                    {
                        mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
                        initialize(mediaplayer)
                        listaMelodi = listaMelodiS
                    }
                }


                //              ecran.setBackgroundColor(Color.rgb(rosu, verde, albastru))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })



        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("BACKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
                if (mediaplayer.isPlaying) {
                    mediaplayer.stop()
                    println("BACKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
                } else {
                    mediaplayer.stop()
                    isEnabled = false // DON'T FORGET THIS!
                    play_music().onBackPressedDispatcher.onBackPressed()
                }
            }
        }
        play_music().onBackPressedDispatcher.addCallback(callback)

        runnable = Runnable {
            seekbar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaplayer.setOnCompletionListener {
            if (!one){
                if (!repeat) {
                    if (!shufle) {
                        i = i + 1
                        if (i > maxMelodi - 1)
                            i = 0

                    } else {
                        i = Random.nextInt(0, maxMelodi)
                    }
                    mediaplayer.release()
                    mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
                    initialize(mediaplayer)
                    listaMelodi = listaMelodiS
                }
                else
                {
                    mediaplayer.release()
                    mediaplayer = createMediaplayer(listaMelodiI)[i] as MediaPlayer
                    initialize(mediaplayer)
                    listaMelodi = listaMelodiS
                }
            }
            else
                mediaplayer.stop()
        }

    }


    private fun initialize(mediaplayer: MediaPlayer) {
        nume_piesa.setText(getName(this.listaMelodiT[i]))
        println("NUmarul i = " + i)

        mediaplayer.prepare()
        mediaplayer.start()
        seekbar.progress = 0
        seekbar.max=mediaplayer.duration

    }


    private fun createMediaplayer(listM: ArrayList<String>?) :MutableList<MediaPlayer> {
        var listaMelodi: MutableList<MediaPlayer> = mutableListOf<MediaPlayer>()
        if (listM != null) {
            //  var item:String = ""
            for (item: String in listM) {

                var MP: MediaPlayer = MediaPlayer()

                MP.setDataSource(item)
                listaMelodi.add(MP)
            }
        }
        return listaMelodi
    }
    private fun getName(nume :String) :String {
        var name = ""
        var k = 1
        var z = 0
        for(car: Char in nume) {

            if (car.equals('/'))
            {
                z = k
            }
            k++
        }
        name = nume.removeRange(k-5,k-1)
        return name.removeRange(0,z)
    }
}

