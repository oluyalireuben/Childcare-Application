package com.example.childcare.models

data class Request(
    val requestId : String = "",
    val parentId : String = "",
    val cgUid : String = "",
    val parent : String = "",
    val idNo : String = "",
    val start : String = "",
    val end : String = "",
    val child : String = "",
    val email : String = "",
    val phone : String = "",
    val location : String = "",
    val age : String = "",
    val services : String = "",
    val extraDuties : String = "",
    val special : String = "",
    val accepted : Boolean = false,
    val declined : Boolean = false
)
