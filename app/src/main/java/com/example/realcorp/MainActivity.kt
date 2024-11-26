package com.example.realcorp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.yaml.snakeyaml.Yaml
import kotlin.random.Random
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private val mediaPlayer = MediaPlayer()
    private var evtConfig : EvtCfg = EvtCfg()

    val eventQuest = mutableListOf<Event>()  // 替换 EventType 为您的事件类型
    val eventDestiny = mutableListOf<Event>()  // 同上，注意检查命名是否正确
    var idxQuest = 0
    var idxDestiny = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        evtConfig = realYamlFile("Cfgs.yaml")
        evtConfig.events.forEach { event ->
            if(event.type == "quest")
                eventQuest.add(event)
            else
                eventDestiny.add(event)
        }
        eventQuest.shuffle(Random)
        eventDestiny.shuffle(Random)

        val buttonQuest = findViewById<Button>(R.id.buttonQuest)
        buttonQuest.setOnClickListener{
            val imageView = findViewById<ImageView>(R.id.imageView)
            val nowEvt = eventQuest[idxQuest]

            val bitmap = readPng(nowEvt.pic + ".png")
            imageView.setImageBitmap(bitmap)

            findViewById<TextView>(R.id.textView).text = nowEvt.name

            initMediaPlayer(nowEvt.mp3 + ".mp3")
            mediaPlayer.start()

            if(nowEvt.reset)
            {
                eventQuest.shuffle(Random)
                idxQuest = 0
            }
            else
            {
                idxQuest++
            }
        }

        val buttonEvt = findViewById<Button>(R.id.buttonEvt)
        buttonEvt.setOnClickListener{
            val imageView = findViewById<ImageView>(R.id.imageView)
            val nowEvt = eventDestiny[idxDestiny]

            val bitmap = readPng(nowEvt.pic + ".png")
            imageView.setImageBitmap(bitmap)

            findViewById<TextView>(R.id.textView).text = nowEvt.name

            initMediaPlayer(nowEvt.mp3 + ".mp3")
            mediaPlayer.start()

            if(nowEvt.reset)
            {
                eventDestiny.shuffle(Random)
                idxDestiny = 0
            }
            else
            {
                idxDestiny++
            }
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

    fun realYamlFile(file : String): EvtCfg{
        var inputStream : InputStream = assets.open(file)
        var yaml = Yaml()
        return yaml.loadAs(inputStream, EvtCfg::class.java)
    }

    fun readPng(file : String): Bitmap{
        var inputStream : InputStream = assets.open(file)
        return BitmapFactory.decodeStream(inputStream)
    }
}
