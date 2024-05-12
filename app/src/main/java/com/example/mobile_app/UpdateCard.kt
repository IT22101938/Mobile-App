package com.example.mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile_app.databinding.ActivityUpdateCardBinding

class UpdateCard : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)

            binding.deleteButton.setOnClickListener{
                DataObject.deleteData(pos)
            }

            binding.updateButton.setOnClickListener {
                //val create_title = binding.createTitle.text.toString()
                //val create_priority = binding.createPriority.text.toString()
                DataObject.updateData(pos, binding.createTitle.text.toString(), binding.createPriority.text.toString())
                //Toast.makeText(this,title+""+priority,Toast.LENGTH_LONG).show()
                myIntent()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
