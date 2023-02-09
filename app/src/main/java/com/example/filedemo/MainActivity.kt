package com.example.filedemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log
import android.widget.Toast
import com.example.filedemo.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.FileNameMap
import java.nio.file.Files.createFile

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val TAG = "FileDemo"
    private val FILENAME = "$TAG.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener { binding.etText.setText("")}// ; delete(FILENAME)} // dient zum Löschen der Datei
        binding.btnSave.setOnClickListener { save(binding.etText.text.toString()) }
        binding.btnLoad.setOnClickListener { load() }

        Log.d(TAG, "filesDir: $filesDir ")
        createFile(filesDir,"A")
        val dirAudio = getDir("audio",Context.MODE_PRIVATE)
        val dirOrte = getDir("orte", MODE_PRIVATE)
        createFile(dirAudio,"B")
        createFile(dirOrte,"Berlin")

        File.createTempFile("MyTmp", "tmp",cacheDir)
        File.createTempFile("MyTmpD", "tmp",cacheDir).deleteOnExit()

    }

    private fun load() {
       val sb = StringBuilder()
        try{
            openFileInput(FILENAME).use { fis ->
                InputStreamReader(fis).use { isr ->
                    BufferedReader(isr).use { br ->
                        while(true){
                            val line = br.readLine()?:break
                            if(sb.isNotEmpty()){
                                sb.append("\n")
                            }
                            sb.append(line)
                        }
                    }
                }
            }
        }catch (e:IOException){
            Toast.makeText(this, "Datei nicht vorhanden, bitte speichern", Toast.LENGTH_SHORT).show()
        }
        binding.etText.setText(sb.toString())

    }

    private fun save(text: String) {
        try {
            openFileOutput(FILENAME, Context.MODE_APPEND).use { fos ->
                OutputStreamWriter(fos).use { osw ->
                    osw.write(text)
                }
            }
        }catch (e:IOException){
            Log.e(TAG, "save: Fehler beim Speichern",e)
        }
    }

    private fun delete(filename:String){
        File(filesDir,filename).delete()
    }

    private fun createFile(path: File, file:String){
        val fullPath = File(path,file)
        FileOutputStream(fullPath).use { fos ->
            fos.write("Hallo".toByteArray())
        }
      //  fullPath.createNewFile() zum Anlegen einer Datei ohne Inhalt
    }
}