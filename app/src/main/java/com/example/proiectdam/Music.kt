package com.example.proiectdam



import android.graphics.Bitmap

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi


import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess


data class Music(val id:String, val title:String, val album:String ,val artist:String, val duration: Long = 0, val path: String,
                 val artUri:String)

class Playlist{
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdBy: String
    lateinit var createdOn: String
}
class MusicPlaylist{
    var ref: ArrayList<Playlist> = ArrayList()
}

fun formatDuration(duration: Long):String{
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) -
            minutes*TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
    return String.format("%02d:%02d", minutes, seconds)
}
fun getImgArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

fun checkPlaylist(playlist: ArrayList<Music>): ArrayList<Music>{
    playlist.forEachIndexed { index, music ->
        val file = File(music.path)
        if(!file.exists())
            playlist.removeAt(index)
    }
    return playlist
}



fun getMainColor(img: Bitmap): Int {
    val newImg = Bitmap.createScaledBitmap(img, 1,1 , true)
    val color = newImg.getPixel(0, 0)
    newImg.recycle()
    return color
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMelodi (): MutableList<MediaPlayer> {
    var listaMelodi: MutableList<MediaPlayer> = mutableListOf<MediaPlayer>()

    val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
    val resourcesPath = Paths.get(projectDirAbsolutePath, "/storage/emulated/0/Download")
    println(" test paths = " + resourcesPath)
    val paths = Files.walk(resourcesPath)
        .filter { item -> Files.isRegularFile(item) }
        .filter { item -> item.toString().endsWith(".mp3") }
        .forEach { item -> println("filename: $item")
            println("item.toString() =  " + item.toString())

            var s:String = item.toString()
            var MP : MediaPlayer = MediaPlayer()
            MP.setDataSource(s)

            listaMelodi.add(MP)
            println("Melodie = "+ MP.toString())


        }

    return listaMelodi

}