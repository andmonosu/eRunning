package com.andmonosu.erunning.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andmonosu.erunning.R
import com.andmonosu.erunning.models.Training

class TrainingAdapter(private val trainingList:List<Training>, private val onClickListener:(Training) -> Unit) : RecyclerView.Adapter<TrainingViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainingViewHolder(layoutInflater.inflate(R.layout.item_training,parent,false))
    }

    override fun getItemCount(): Int = trainingList.size

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val item = trainingList[position]
        holder.render(item, onClickListener)
    }
}