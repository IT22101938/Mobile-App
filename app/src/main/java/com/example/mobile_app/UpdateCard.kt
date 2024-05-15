package com.example.mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(applicationContext, myDatabase::class.java, "To_Do").build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            val day = DataObject.getData(pos).day
            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)
            binding.createDay.setText(day)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        database.dao().deleteTask(
                            Entity(
                                pos + 1,
                                binding.createTitle.text.toString(),
                                binding.createPriority.text.toString(),
                                binding.createDay.text.toString()
                            )
                        )
                    }
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                val newTitle = binding.createTitle.text.toString().trim()
                val newPriority = binding.createPriority.text.toString().trim()
                val newDay = binding.createDay.text.toString().trim()

                if (newTitle.isNotEmpty() && newPriority.isNotEmpty() && newDay.isNotEmpty()) {
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
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}