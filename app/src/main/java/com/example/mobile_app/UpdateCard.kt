package com.example.mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import com.example.mobile_app.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class UpdateCard : AppCompatActivity() {
    private lateinit var database: myDatabase
    private lateinit var binding: ActivityUpdateCardBinding



    private val validDaysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(applicationContext, myDatabase::class.java, "To_Do").build()

        // Set up the Spinner for priority selection
        val priorityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        )
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createPriority.adapter = priorityAdapter

        // Set up the Spinner for day selection
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, validDaysOfWeek)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.createDay.adapter = dayAdapter

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val task = DataObject.getData(pos)
            val title = task.title
            val priority = task.priority
            val day = task.day

            binding.createTitle.setText(title)
            binding.createDay.setSelection(dayAdapter.getPosition(day))



            // Set the Spinner selection based on the priority value
            val priorityPosition = priorityAdapter.getPosition(priority)
            binding.createPriority.setSelection(priorityPosition)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        database.dao().deleteTask(
                            Entity(
                                pos + 1,
                                binding.createTitle.text.toString(),
                                binding.createPriority.selectedItem.toString(),
                                binding.createDay.selectedItem.toString()

                            )
                        )
                    }
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                val newTitle = binding.createTitle.text.toString().trim()
                val newPriority = binding.createPriority.selectedItem.toString()
                val newDay = binding.createDay.selectedItem.toString()

                if (newTitle.isNotEmpty() && newPriority.isNotEmpty() && newDay.isNotEmpty() && validDaysOfWeek.contains(newDay)) {
                    DataObject.updateData(pos, newTitle, newPriority, newDay)
                    GlobalScope.launch {
                        withContext(Dispatchers.IO) {
                            database.dao().updateTask(
                                Entity(
                                    pos + 1,
                                    newTitle,
                                    newPriority,
                                    newDay
                                )
                            )
                        }
                    }
                    myIntent()
                } else {
                    Toast.makeText(this, "Please fill in all the fields ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
        private fun myIntent() {
          val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


}



