package com.example.childcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.childcare.R
import com.example.childcare.models.Caregiver
import com.example.childcare.models.Request
import com.example.childcare.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyRequestsAdapter (
    private val requests : ArrayList<Request>
): RecyclerView.Adapter<MyRequestsAdapter.MyRequestsViewHolder>(){

    inner class MyRequestsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val cgName : TextView = itemView.findViewById(R.id.caregiver)
        val cgLocation : TextView = itemView.findViewById(R.id.loc)

        fun bind(caregiver: Caregiver) {
            val cgUid = caregiver.cgUid

            FirebaseDatabase.getInstance().getReference("Caregivers").child(cgUid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(Caregiver::class.java)!!.name
                    val location = snapshot.getValue(Caregiver::class.java)!!.location

                    cgName.text = name
                    cgLocation.text = location
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestsViewHolder {
        return MyRequestsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_requests_item , parent , false))
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: MyRequestsViewHolder, position: Int) {

    }
}