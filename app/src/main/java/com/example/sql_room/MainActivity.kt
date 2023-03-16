package com.example.sql_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.sql_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val db = MainDb.getDb(this)
        db.getDao().getAllItems().asLiveData().observe(this){list ->
            bind.twList.text = ""
            list.forEach{
                val text = "Id: ${it.id} name: ${it.name} Price: ${it.price}\n"
                bind.twList.append(text)
            }
        }
        bind.button.setOnClickListener{
            val item = Item(null,
                bind.edName.text.toString(),
                bind.edPrice.text.toString()
            )
            Thread{
                db.getDao().insertItem(item)
            }.start()
            bind.edName.setText("")
            bind.edPrice.setText("")



        }


    }
}