package com.example.childcare.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.childcare.R
import javax.inject.Inject

class RequestsAdapter @Inject constructor(
    private val requests : ArrayList<com.example.childcare.models.Request>,
    private val onRequestClick: OnRequestClick,
    private val context: Context
): RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder>(){

    interface OnRequestClick {
        fun onRequestClicked(request: com.example.childcare.models.Request)
    }
    inner class RequestsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.child)
        private val age : TextView = itemView.findViewById(R.id.childAge)
        private val location : TextView = itemView.findViewById(R.id.location)
        private val status : TextView = itemView.findViewById(R.id.status)
        private val declined : TextView = itemView.findViewById(R.id.declined)

        fun bind(request : com.example.childcare.models.Request) {
            name.text = request.parent
            age.text = "${request.age}${context.getString(R.string.years)}"
            location.text = request.location
            status.visibility = if (request.declined) View.GONE else View.VISIBLE
            status.text = if (request.accepted) "Ongoing" else "Pending"
            status.setTextColor(Color.GREEN)

            declined.visibility = if (request.declined) View.VISIBLE else View.GONE
            declined.setTextColor(Color.RED)
            itemView.setOnClickListener { onRequestClick.onRequestClicked(request) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        return RequestsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.requests_item , parent , false))
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.bind(requests[position])
    }
}