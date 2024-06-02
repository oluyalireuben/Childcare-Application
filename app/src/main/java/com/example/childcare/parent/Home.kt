package com.example.childcare.parent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.childcare.R
import com.example.childcare.adapters.CaregiversAdapter
import com.example.childcare.common.Login
import com.example.childcare.databinding.FragmentHomeBinding
import com.example.childcare.models.Caregiver
import com.example.childcare.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var caregivers: ArrayList<Caregiver>
    private lateinit var filteredCaregivers: ArrayList<Caregiver>
    private lateinit var onCaregiverClick: CaregiversAdapter.OnCaregiverClick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        caregivers = ArrayList()
        filteredCaregivers = ArrayList()

        binding.careGiversRV.setHasFixedSize(true)
        binding.careGiversRV.layoutManager = LinearLayoutManager(requireContext())

        binding.logout.setOnClickListener { showDialog() }

        onCaregiverClick = object : CaregiversAdapter.OnCaregiverClick {
            override fun onCaregiverClicked(caregiver: Caregiver) {
                startActivity(
                    Intent(requireContext(), CaregiverDetails::class.java)
                        .putExtra("name", caregiver.name)
                        .putExtra("services", caregiver.services)
                        .putExtra("means", caregiver.meansOfPayment)
                        .putExtra("rates", caregiver.hourlyRates)
                        .putExtra("qualifications", caregiver.qualifications)
                        .putExtra("cgUid", caregiver.cgUid)
                )
            }

        }

        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchCaregiver(newText)
                return true
            }

        })

        FirebaseDatabase.getInstance().getReference("App users/${FirebaseAuth.getInstance().currentUser!!.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val parent = snapshot.getValue(User::class.java)!!.name
                    binding.greetings.text = getString(R.string.welcome, parent)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext() , error.message , Toast.LENGTH_SHORT).show()
                }

            })

        FirebaseDatabase.getInstance().getReference("Caregivers").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->

                    caregivers.add(dataSnapshot.getValue(Caregiver::class.java)!!)
                }
                filteredCaregivers.addAll(caregivers)
                if (filteredCaregivers.isNotEmpty()) {
                    binding.careGiversRV.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.empty.visibility = View.GONE
                } else {
                    binding.careGiversRV.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.empty.visibility = View.VISIBLE
                }

                binding.careGiversRV.adapter = CaregiversAdapter(
                    caregivers = filteredCaregivers,
                    onCaregiverClick = onCaregiverClick
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext() , error.message , Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun searchCaregiver(query: String) {
        filteredCaregivers.clear()
        if (query.isEmpty()) {
            filteredCaregivers.addAll(caregivers)
            binding.careGiversRV.adapter =
                CaregiversAdapter(caregivers = caregivers, onCaregiverClick = onCaregiverClick)
        } else {
            val filteredList = caregivers.filter { caregiver ->
                caregiver.name.contains(query, ignoreCase = true) ||
                        caregiver.location.contains(query, ignoreCase = true)
            }
            filteredCaregivers.addAll(filteredList)
            binding.careGiversRV.adapter = CaregiversAdapter(
                caregivers = filteredCaregivers,
                onCaregiverClick = onCaregiverClick
            )
        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Logout")
            .setMessage("Are you sure to logout?")
            .setPositiveButton(
                "Confirm"
            ) { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                requireContext().startActivity(Intent(requireContext() , Login::class.java))
                activity?.finish()
                dialog.dismiss()
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()

    }
}