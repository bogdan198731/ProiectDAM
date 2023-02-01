package com.example.proiectdam

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.lista.*
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Paths

class Lista: AppCompatActivity(), Serializable  {


    lateinit var runnable: Runnable
    var i:Int = 0

    var shufle:Boolean = false
    var repeat:Boolean = false
    var maxMelodi:Int = 0
    private var handler = Handler()
    val butonPlay :ArrayList<Button> = ArrayList()
    val buttonAdd :ArrayList<Button> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista)


        val intent = intent
        var playAll = intent.getBooleanExtra("key", false)


        if(requestRuntimePermission()){
            println("Cere permisiune")
        }

        var listaMelodi = getMelodi()

        maxMelodi = listaMelodi.size - 1

        println("Piese gasite = " + maxMelodi)

        var myIntent :Intent = Intent(this@Lista, play_music::class.java)
        myIntent.putExtra("key", listaMelodi)
        myIntent.putExtra("key2", maxMelodi)
        myIntent.putExtra("key3", i)
        myIntent.putExtra("key4", false)




        for (but in butonPlay)
        {print("Click buton = " + but.id)
            but.setOnClickListener {
                print("Click buton = " + but.id)
                myIntent.putExtra("key3", but.id)
                myIntent.putExtra("key4", true)
                this.startActivity(myIntent)
            }
        }

        for (but in buttonAdd)
        {print("Click buton = " + but.id)
            but.setOnClickListener {
                print("Click buton = " + but.id)
                myIntent.putExtra("key3", but.id)
                this.startActivity(myIntent)
            }
        }
        if(playAll)
            this.startActivity(myIntent)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getMelodi (): ArrayList<String> {

        var listaMelodi: ArrayList<String> = ArrayList<String>()

        var k = 0
        val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectDirAbsolutePath, "/storage/emulated/0/Download")
        println(" test paths = " + resourcesPath)
        val paths = Files.walk(resourcesPath)
            .filter { item -> Files.isRegularFile(item) }
            .filter { item -> item.toString().endsWith(".mp3") }
            .forEach { item -> println("filename: $item")

                listaMelodi.add(item.toString())

                val layL = LinearLayout(this)

                    var nume = getName(item.toString())

                println("nume melodie = " + nume)


                    layL.setBackgroundColor(20)
                    layL.setAddStatesFromChildren(true)
                    layL.gravity = 10

                    val butPlay = Button(this)
                    butPlay.id = k++

                println("butPlay.id = " + butPlay.id)

                    butPlay.height = 80
                    butPlay.setText(nume)

                    val butAdd = Button(this)
                    butAdd.width = 500

                    butAdd.setText("Add")
                    butAdd.id = k
                    butonPlay.add(butPlay)
                    buttonAdd.add(butAdd)

                    layL.addView(butPlay, 1200, 120)
                    layL.addView(butAdd, 100, 120)
                    lista_layout.addView(layL)


            }


        return listaMelodi

    }

    private fun requestRuntimePermission() :Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
            return false
        }
        return true
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