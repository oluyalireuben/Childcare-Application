package com.example.childcare.caregiver

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
import com.example.childcare.adapters.RequestsAdapter
import com.example.childcare.databinding.FragmentRequestsBinding
import com.example.childcare.models.Request
import com.example.childcare.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RequestsFragment : Fragment() {

    private lateinit var binding : FragmentRequestsBinding
    private lateinit var requests : ArrayList<Request>
    private lateinit var onRequestClick: RequestsAdapter.OnRequestClick

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requests = ArrayList()

        binding.requestsRV.setHasFixedSize(true)
        binding.requestsRV.layoutManager = LinearLayoutManager(requireContext())

        binding.logout.setOnClickListener {
            showDialog()
        }
        onRequestClick = object : RequestsAdapter.OnRequestClick {
            override fun onRequestClicked(request: Request) {
                startActivity(
                    Intent(requireContext() , RequestDetails::class.java)
                    .putExtra("parent" , request.parent)
                    .putExtra("child" , request.child)
                    .putExtra("id" , request.idNo)
                    .putExtra("phone" , request.phone)
                    .putExtra("email" , request.email)
                    .putExtra("services" , request.services)
                    .putExtra("duties" , request.extraDuties)
                    .putExtra("location" , request.location)
                    .putExtra("special" , request.special)
                    .putExtra("age" , request.age)
                    .putExtra("start" , request.start)
                    .putExtra("end" , request.end)
                    .putExtra("requestId" , request.requestId)
                    .putExtra("accepted" , request.accepted)
                    .putExtra("declined" , request.declined)
                )
            }

        }

        FirebaseDatabase.getInstance().getReference("App users/${FirebaseAuth.getInstance().currentUser!!.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val caregiver = snapshot.getValue(User::class.java)!!.name
                    binding.greetings.text = getString(R.string.welcome, caregiver)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        FirebaseDatabase.getInstance().getReference("Requests").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                requests.clear()
                snapshot.children.forEach { dataSnapshot ->
                    if (FirebaseAuth.getInstance().currentUser!!.uid == dataSnapshot.getValue(Request::class.java)!!.cgUid) {
                        requests.add(dataSnapshot.getValue(Request::class.java)!!)
                    }
                }

                if (requests.isNotEmpty()) {
                    binding.requestsRV.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                    binding.empty.visibility = View.GONE
                } else {
                    binding.requestsRV.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    binding.empty.visibility = View.VISIBLE
                }

                binding.requestsRV.adapter = RequestsAdapter(
                    requests = requests ,
                    onRequestClick = onRequestClick ,
                    context = requireContext()
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext() , error.message , Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Logout")
            .setMessage("Are you sure to logout?")
            .setPositiveButton(
                "Confirm"
            ) { dialog, _ ->
                Toast.makeText(requireContext() , "Signed out" , Toast.LENGTH_LONG).show()
                activity?.finishAffinity()
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