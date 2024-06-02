package com.example.childcare.auxiliary

import com.google.firebase.database.FirebaseDatabase

class RequestsHelper(
    uid : String
) {
    private val database = FirebaseDatabase.getInstance().reference
    private val requestId : String = database.child("Requests").push().key!!
    private val requestsReference = database.child("Requests")

    fun editAccepted(requestId : String) {
        requestsReference.child(requestId).child("accepted").setValue(true)
    }

    fun editDeclined(requestId : String) {
        requestsReference.child(requestId).child("declined").setValue(true)
    }

}