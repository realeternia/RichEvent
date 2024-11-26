package com.example.realcorp

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonQuest = findViewById<Button>(R.id.buttonQuest)
        buttonQuest.setOnClickListener{
            val imageView = findViewById<ImageView>(R.id.imageView)

            // 你可以在这里对imageView进行操作，比如设置图片
            imageView.setImageResource(R.drawable.ufo)
            findViewById<TextView>(R.id.textView).text = "外星人进攻地球"

            initMediaPlayer("ufo.mp3")
            mediaPlayer.start()
        }

        val buttonEvt = findViewById<Button>(R.id.buttonEvt)
        buttonEvt.setOnClickListener{
            val imageView = findViewById<ImageView>(R.id.imageView)
            // 你可以在这里对imageView进行操作，比如设置图片
            imageView.setImageResource(R.drawable.laji)
            findViewById<TextView>(R.id.textView).text = "随地乱扔垃圾，罚款300元"

            initMediaPlayer("laji.mp3")
            mediaPlayer.start()
        }

    }

    fun initMediaPlayer(name: String){
        var assetM = assets
        var fd = assetM.openFd(name)
        mediaPlayer.reset()
        Log.d("d", "off" + fd.startOffset)
        Log.d("d", "len" + fd.length)
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
        mediaPlayer.seekTo(3300) // 跳过语音提示
    }
}
