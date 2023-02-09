package com.example.filedemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.filedemo.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.net.FileNameMap

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val TAG = "FileDemo"
    private val FILENAME = "$TAG.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "filesDir: $filesDir ")
        createFile(filesDir,"A")
        val dirAudio = getDir("audio",Context.MODE_PRIVATE)
        val dirOrte = getDir("orte", MODE_PRIVATE)
        createFile(dirAudio,"B")
        createFile(dirOrte,"Berlin")

        File.createTempFile("MyTmp", "tmp",cacheDir)
        File.createTempFile("MyTmpD", "tmp",cacheDir).deleteOnExit()

    }

    private fun createFile(path: File, file:String){
        val fullPath = File(path,file)
        FileOutputStream(fullPath).use { fos ->
            fos.write("Hallo".toByteArray())
        }
    }
}