package com.example.childcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.childcare.R
import com.example.childcare.models.Caregiver

class CaregiversAdapter (
    private val caregivers : ArrayList<Caregiver>,
    private val onCaregiverClick: OnCaregiverClick
): RecyclerView.Adapter<CaregiversAdapter.CaregiversVieHolder>(){

    interface OnCaregiverClick {
        fun onCaregiverClicked(caregiver: Caregiver)
    }
    inner class CaregiversVieHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.child)
        val location : TextView = itemView.findViewById(R.id.loc)

        fun bind(caregiver: Caregiver) {
            name.text = caregiver.name
            location.text = caregiver.location

            itemView.setOnClickListener { onCaregiverClick.onCaregiverClicked(caregiver) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaregiversVieHolder {
        return CaregiversVieHolder(LayoutInflater.from(parent.context).inflate(R.layout.care_giver_item , parent , false))
    }

    override fun getItemCount(): Int {
        return caregivers.size
    }

    override fun onBindViewHolder(holder: CaregiversVieHolder, position: Int) {
        holder.bind(caregivers[position])
    }
}