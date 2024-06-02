package com.example.childcare.caregiver

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.childcare.R
import com.example.childcare.auxiliary.RequestsHelper
import com.example.childcare.databinding.ActivityRequestDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class RequestDetails : AppCompatActivity() {
    private lateinit var binding : ActivityRequestDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestHelper = RequestsHelper(FirebaseAuth.getInstance().currentUser!!.uid)

        val parent = intent.getStringExtra("parent").toString()
        val child = intent.getStringExtra("child").toString()
        val id = intent.getStringExtra("id").toString()
        val email = intent.getStringExtra("email").toString()
        val phone = intent.getStringExtra("phone").toString()
        val age = intent.getStringExtra("age").toString()
        val duties = intent.getStringExtra("duties").toString()
        val services = intent.getStringExtra("services").toString()
        val special = intent.getStringExtra("special").toString()
        val location = intent.getStringExtra("location").toString()
        val start = intent.getStringExtra("start").toString()
        val end = intent.getStringExtra("end").toString()
        val requestId = intent.getStringExtra("requestId").toString()
        val accepted = intent.getBooleanExtra("accepted" , false)
        val declined = intent.getBooleanExtra("declined" , false)

        binding.ageV.text = getString(R.string.year, age)
        binding.idNoV.text = id
        binding.phoneV.text = phone
        binding.emailV.text = email
        binding.residenceV.text = location
        binding.servicesV.text = services
        binding.dutiesV.text = duties
        binding.specialV.text = special
        binding.startV.text = start
        binding.endV.text = end
        binding.parentNameV.text = parent
        binding.childNameV.text = child

        if (accepted || declined) {
            binding.accept.visibility = View.GONE
            binding.decline.visibility = View.GONE
        }
        binding.accept.setOnClickListener {
            requestHelper.editAccepted(requestId)

        }
        binding.decline.setOnClickListener {
            requestHelper.editDeclined(requestId)

        }
    }
}