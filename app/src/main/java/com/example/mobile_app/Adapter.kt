package com.example.mobile_app

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_app.databinding.ViewBinding

class Adapter(private var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cardInfo: CardInfo) {
            binding.title.text = cardInfo.title
            binding.priority.text = cardInfo.priority

            val context = binding.root.context

            when (cardInfo.priority.toLowerCase()) {
                "high" -> binding.mylayout.setBackgroundColor(Color.parseColor("#F05454"))
                "medium" -> binding.mylayout.setBackgroundColor(Color.parseColor("#EDC988"))
                else -> binding.mylayout.setBackgroundColor(Color.parseColor("#00917C"))
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, UpdateCard::class.java)
                intent.putExtra("id", adapterPosition)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}
