package com.example.mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobile_app.databinding.ActivityCreateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCard : AppCompatActivity() {
    private lateinit var database: myDatabase
    private lateinit var binding: ActivityCreateCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        binding.saveButton.setOnClickListener{
            if (binding.createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createPriority.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createDay.text.toString().trim { it <= ' ' }.isNotEmpty()
            ){
                val title = binding.createTitle.text.toString()
                val priority = binding.createPriority.text.toString()
                val day = binding.createDay.text.toString()
                DataObject.setData(title, priority, day)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority,day)) }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}