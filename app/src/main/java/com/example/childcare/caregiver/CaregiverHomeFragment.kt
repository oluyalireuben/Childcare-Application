package com.example.childcare.caregiver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.childcare.databinding.FragmentCaregiverHomeBinding
import com.example.childcare.models.Caregiver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CaregiverHomeFragment : Fragment() {

    private lateinit var binding: FragmentCaregiverHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaregiverHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.upload.setOnClickListener {
            verifyFields()
        }
    }

    private fun verifyFields() {
        if (binding.name.text.toString().isEmpty()) {
            binding.name.error = "This is a required field"
            binding.name.requestFocus()
            return
        }
        if (binding.qualifications.text.toString().isEmpty()) {
            binding.qualifications.error = "This is a required field"
            binding.qualifications.requestFocus()
            return
        }
        if (binding.services.text.toString().isEmpty()) {
            binding.services.error = "This is a required field"
            binding.services.requestFocus()
            return
        }
        if (binding.rates.text.toString().isEmpty()) {
            binding.rates.error = "This is a required field"
            binding.rates.requestFocus()
            return
        }
        if (binding.means.text.toString().isEmpty()) {
            binding.means.error = "This is a required field"
            binding.means.requestFocus()
            return
        }
        if (binding.location.text.toString().isEmpty()) {
            binding.location.error = "This is a required field"
            binding.location.requestFocus()
            return
        }

        binding.progressBar6.visibility = View.VISIBLE
        uploadInfo()
    }

    private fun uploadInfo() {
        FirebaseDatabase.getInstance()
            .getReference("Caregivers/${FirebaseAuth.getInstance().currentUser!!.uid}")
            .setValue(
                Caregiver(
                    name = binding.name.text.toString(),
                    qualifications = binding.qualifications.text.toString(),
                    services = binding.services.text.toString(),
                    hourlyRates = binding.rates.text.toString(),
                    meansOfPayment = binding.means.text.toString(),
                    location = binding.location.text.toString(),
                    cgUid = FirebaseAuth.getInstance().currentUser!!.uid
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.progressBar6.visibility = View.GONE
                    binding.qualifications.setText("")
                    binding.services.setText("")
                    binding.rates.setText("")
                    binding.means.setText("")
                    binding.location.setText("")
                    binding.location.clearFocus()

                    Toast.makeText(requireContext(), "Uploaded successfully", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(requireContext(), CareGiverDashboard::class.java))
                } else {
                    binding.progressBar6.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        task.exception!!.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}