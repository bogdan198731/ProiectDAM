package com.example.proiectdam

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lista.*
import kotlinx.android.synthetic.main.music_play.*
import java.nio.file.Files
import java.nio.file.Paths

// to do
// lista melodi
// play list
// buton repeat
// schimbare ordine butoane
// permisiuni
// posibilitati radio

class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var myIntentLista: Intent = Intent(this@MainActivity, Lista::class.java)
        myIntentLista.putExtra("key", false)

        get_Lista_Piese.setOnClickListener {
            this.startActivity(myIntentLista)
        }

        Play_all.setOnClickListener {
            myIntentLista.putExtra("key", true)
            this.startActivity(myIntentLista)

        }

        var myIntentRadio: Intent = Intent(this@MainActivity, Radio::class.java)
        Play_radio.setOnClickListener {
            myIntentLista.putExtra("key", true)
            this.startActivity(myIntentRadio)

        }

        var myIntentInternet: Intent = Intent(this@MainActivity, Internet::class.java)
        Play_internet.setOnClickListener {
            myIntentLista.putExtra("key", true)
            this.startActivity(myIntentInternet)

        }

        var myIntentAbout: Intent = Intent(this@MainActivity, About::class.java)
        btn_About.setOnClickListener {

            this.startActivity(myIntentAbout)

        }
    }
}




