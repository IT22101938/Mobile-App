package com.example.mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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

        val priorityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        )
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createPriority.adapter = priorityAdapter



        binding.saveButton.setOnClickListener {
            val title = binding.createTitle.text.toString().trim()
            val priority = binding.createPriority.selectedItem.toString()
            val day = binding.createDay.text.toString().trim()

            if (title.isNotEmpty() && priority.isNotEmpty() && day.isNotEmpty()) {
                DataObject.setData(title, priority, day)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority, day))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}