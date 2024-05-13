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
            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)

            binding.deleteButton.setOnClickListener {
                DataObject.deleteData(pos)
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        database.dao().deleteTask(
                            Entity(
                                pos + 1,
                                binding.createTitle.text.toString(),
                                binding.createPriority.text.toString()
                            )
                        )
                    }
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                DataObject.updateData(pos, binding.createTitle.text.toString(), binding.createPriority.text.toString()
                )
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        database.dao().updateTask(
                            Entity(
                                pos + 1,
                                binding.createTitle.text.toString(),
                                binding.createPriority.text.toString()
                            )
                        )
                    }
                }
                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}