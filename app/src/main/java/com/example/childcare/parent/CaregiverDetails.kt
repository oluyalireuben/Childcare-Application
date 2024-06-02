package com.example.childcare.parent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.childcare.databinding.ActivityCaregiverDetailsBinding

class CaregiverDetails : AppCompatActivity() {
    private lateinit var binding: ActivityCaregiverDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaregiverDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name").toString()
        val services = intent.getStringExtra("services").toString()
        val means = intent.getStringExtra("means").toString()
        val rates = intent.getStringExtra("rates").toString()
        val qualifications = intent.getStringExtra("qualifications").toString()
        val cgUid = intent.getStringExtra("cgUid").toString()

        binding.caregiver.text = name
        binding.servicesV.text = services
        binding.meansV.text = means
        binding.FeesV.text = rates
        binding.qualificationsV.text = qualifications

        binding.arrowBack.setOnClickListener {
            onNavigateUp()
        }

        binding.request.setOnClickListener {
            startActivity(Intent(this , BookingRequest::class.java)
                .putExtra("cgUid" , cgUid)
            )
        }

    }
}