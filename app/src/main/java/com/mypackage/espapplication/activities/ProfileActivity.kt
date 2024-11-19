package com.mypackage.espapplication.activities

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.getOrAddAdapter
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mypackage.espapplication.R
import com.mypackage.espapplication.activities.ui.theme.ESPApplicationTheme
import com.mypackage.espapplication.adapters.mAdapter
import com.mypackage.espapplication.models.Parameter
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
        /*
        dbProfRef.child("Profile1").setValue(Profile("@554562","newProfile",
            Parameter(125.0,48.0),Parameter(89.2,54.0),Parameter(12.3,-2.3),Parameter(54.2,23.1),Parameter(54.8,25.1),
            Parameter(87.1,69.3)))*/
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
                pAdapter.setOnProfileClickListener(object : mAdapter.onProfileButtonClickListener{
                    override fun onProfileClick(position: Int) {
                        setResult(RESULT_OK,Intent().putExtra("profile",profileList[position]))
                        finish()
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
