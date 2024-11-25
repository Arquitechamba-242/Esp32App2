package com.mypackage.espapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mypackage.espapplication.R
import com.mypackage.espapplication.adapters.mAdapter
import com.mypackage.espapplication.models.Profile

class ProfileActivity : ComponentActivity() {
    private lateinit var profilesRecyclerView : RecyclerView
    private lateinit var newProfileButton : Button
    private lateinit var profileList : ArrayList<Profile>
    private lateinit var dbProfRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        initAll()
        newProfileButton.setOnClickListener {
            val intentToCreateProfileActivity : Intent = Intent(this@ProfileActivity,CreateProfileActivity::class.java)
            startActivity(intentToCreateProfileActivity)
        }
        getProfileData()
    }

    private fun getProfileData() {
        dbProfRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                profileList.clear()
                if(snapshot.exists()){
                    for (profSnap in snapshot.children){
                        val profData = profSnap.getValue(Profile::class.java)
                        profileList.add(profData!!)
                    }
                }
                val pAdapter = mAdapter(profileList)
                profilesRecyclerView.adapter = pAdapter
                pAdapter.setOnProfileButtonClickListener(object : mAdapter.OnProfileButtonClickListener{
                    override fun onButtonProfileClick(position: Int) {
                        setResult(RESULT_OK,Intent().putExtra("profile",profileList[position]))
                        finish()
                    }
                })
                pAdapter.setOnProfileViewClickListener(object : mAdapter.OnProfileViewClickListener{
                    override fun onProfileViewClick(position: Int) {
                        val intentToDisplayProfile = Intent(this@ProfileActivity,DisplayProfileActivity::class.java)
                        intentToDisplayProfile.putExtra("profileSelected",profileList[position])
                        startActivity(intentToDisplayProfile)
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initAll() {
        dbProfRef = FirebaseDatabase.getInstance().getReference("Profiles")
        profilesRecyclerView = findViewById(R.id.recyclerViewProfiles)
        newProfileButton = findViewById(R.id.createNewProfileButton)
        profileList = arrayListOf<Profile>()
        profilesRecyclerView.layoutManager = LinearLayoutManager(this)
        profilesRecyclerView.setHasFixedSize(true)
    }


}
