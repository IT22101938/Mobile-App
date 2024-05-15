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

    val validDaysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        // Set up the Spinner for day selection
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, validDaysOfWeek)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createDay.adapter = dayAdapter

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
            val day = binding.createDay.selectedItem.toString()

            if (title.isNotEmpty() && priority.isNotEmpty() && day.isNotEmpty() && validDaysOfWeek.contains(day)) {
                DataObject.setData(title, priority, day)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority, day))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all the fields ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}