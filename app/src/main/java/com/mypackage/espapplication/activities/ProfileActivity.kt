package com.mypackage.espapplication.activities

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.espapplication.R
import com.mypackage.espapplication.activities.ui.theme.ESPApplicationTheme

class ProfileActivity : ComponentActivity() {
    lateinit var profilesRecyclerView : RecyclerView
    lateinit var newProfileButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        initAll()

        newProfileButton.setOnClickListener {

        }

    }

    private fun initAll() {
        profilesRecyclerView = findViewById(R.id.recyclerViewProfiles)
        newProfileButton = findViewById(R.id.createNewProfileButton)
    }


}
