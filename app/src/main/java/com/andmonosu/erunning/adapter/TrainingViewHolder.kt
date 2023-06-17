package com.andmonosu.erunning.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.andmonosu.erunning.R
import com.andmonosu.erunning.databinding.ItemTrainingBinding
import com.andmonosu.erunning.models.Training

class TrainingViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemTrainingBinding.bind(view)

    fun render(trainingModel: Training, onClickListener:(Training) -> Unit){
        binding.tvTrainingName.text = trainingModel.name
        itemView.setOnClickListener { onClickListener(trainingModel) }
    }
}