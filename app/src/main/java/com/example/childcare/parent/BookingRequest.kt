package com.example.childcare.parent

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.childcare.databinding.ActivityBookingRequestBinding
import com.example.childcare.models.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class BookingRequest : AppCompatActivity() {
    private lateinit var binding : ActivityBookingRequestBinding
    private lateinit var cgUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cgUid = intent.getStringExtra("cgUid").toString()

        binding.submit.setOnClickListener { validateFields() }

        binding.imageView2.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.start.setText(selectedDate)
                }, year, month, day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        binding.imageView3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.end.setText(selectedDate)
                }, year, month, day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

    }

    private fun validateFields() {
        if (binding.parentName.text.toString().isEmpty()) {
            binding.parentName.error = " This is a required field"
            binding.parentName.requestFocus()
            return
        }
        if (binding.idNo.text.toString().isEmpty()) {
            binding.idNo.error = " This is a required field"
            binding.idNo.requestFocus()
            return
        }
        if (binding.phone.text.toString().isEmpty()) {
            binding.phone.error = " This is a required field"
            binding.phone.requestFocus()
            return
        }
        if (binding.email.text.toString().isEmpty()) {
            binding.email.error = " This is a required field"
            binding.email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()) {
            binding.email.error = " Enter a valid email address"
            binding.email.requestFocus()
            return
        }
        if (binding.child.text.toString().isEmpty()) {
            binding.child.error = " This is a required field"
            binding.child.requestFocus()
            return
        }
        if (binding.age.text.toString().isEmpty()) {
            binding.age.error = " This is a required field"
            binding.age.requestFocus()
            return
        }
        if (binding.start.text.toString().isEmpty()) {
            binding.start.error = " This is a required field"
            binding.start.requestFocus()
            return
        }
        if (binding.end.text.toString().isEmpty()) {
            binding.end.error = " This is a required field"
            binding.end.requestFocus()
            return
        }
        if (binding.services.text.toString().isEmpty()) {
            binding.services.error = " This is a required field"
            binding.services.requestFocus()
            return
        }
        if (binding.location.text.toString().isEmpty()) {
            binding.location.error = " This is a required field"
            binding.location.requestFocus()
            return
        }
        binding.progressBar6.visibility = View.VISIBLE
        uploadDetails()
    }

    private fun uploadDetails() {
         val database = FirebaseDatabase.getInstance().reference
         val requestId = database.child("Requests").push().key!!
        FirebaseDatabase.getInstance().getReference("Requests/$requestId")
            .setValue(Request(
                parentId = FirebaseAuth.getInstance().currentUser!!.uid,
                cgUid = cgUid,
                requestId = requestId,
                parent = binding.parentName.text.toString(),
                idNo = binding.idNo.text.toString(),
                start = binding.start.text.toString(),
                end = binding.end.text.toString(),
                child = binding.child.text.toString(),
                email = binding.email.text.toString(),
                phone = binding.phone.text.toString(),
                location = binding.location.text.toString(),
                age = binding.age.text.toString(),
                services = binding.services.text.toString(),
                extraDuties = binding.additional.text.toString().ifEmpty { "N/A" },
                special = binding.specialNeeds.text.toString().ifEmpty { "N/A" }

            )).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.progressBar6.visibility = View.GONE
                    binding.parentName.setText("")
                    binding.idNo.setText("")
                    binding.start.setText("")
                    binding.end.setText("")
                    binding.child.setText("")
                    binding.email.setText("")
                    binding.phone.setText("")
                    binding.location.setText("")
                    binding.location.clearFocus()
                    binding.age.setText("")
                    binding.services.setText("")
                    binding.additional.setText("")
                    binding.specialNeeds.setText("")

                    Toast.makeText(this , "Uploaded successfully" , Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this , ParentDashboard::class.java))
                } else {
                    binding.progressBar6.visibility = View.GONE
                    Toast.makeText(this , task.exception!!.localizedMessage , Toast.LENGTH_SHORT).show()
                }
            }
    }
}