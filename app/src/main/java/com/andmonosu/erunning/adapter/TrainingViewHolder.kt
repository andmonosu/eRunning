package com.andmonosu.erunning.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andmonosu.erunning.R
import com.andmonosu.erunning.databinding.ItemTrainingBinding
import com.andmonosu.erunning.data.model.Training

class TrainingViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemTrainingBinding.bind(view)

    fun render(trainingModel: Training, onClickListener:(Training) -> Unit){
        binding.tvTrainingName.text = trainingModel.name
        if(trainingModel.isActive){
            binding.tvTrainingName.setTextColor(ContextCompat.getColor(itemView.context, R.color.background_button))
        }
        itemView.setOnClickListener { onClickListener(trainingModel) }
    }
}