package com.example.childcare.auxiliary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper(
    uid : String
) {
    private val database = FirebaseDatabase.getInstance().reference
    private val requestId : String = database.child("Requests").push().key!!
    private val requestsReference = database.child("Requests").child(uid).child(requestId)

    fun fetchDetails() {
        requestsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}