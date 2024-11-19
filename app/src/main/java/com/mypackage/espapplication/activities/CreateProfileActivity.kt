package com.mypackage.espapplication.activities

import android.content.res.Resources
import android.os.Bundle
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.espapplication.R
import com.mypackage.espapplication.activities.ui.theme.ESPApplicationTheme
import com.mypackage.espapplication.models.Parameter
import com.mypackage.espapplication.models.Profile
import java.util.DoubleSummaryStatistics

class CreateProfileActivity : ComponentActivity() {
    private lateinit var dbPushProfile : DatabaseReference

    private lateinit var warningTextView : TextView
    private lateinit var editNameProfile : EditText
    private lateinit var editMaxAlcohol : EditText
    private lateinit var editMinAlcohol : EditText
    private lateinit var editMaxAltitud : EditText
    private lateinit var editMinAltitud : EditText
    private lateinit var editMaxCO2 : EditText
    private lateinit var editMinCO2 : EditText
    private lateinit var editMaxHumedad : EditText
    private lateinit var editMinHumedad : EditText
    private lateinit var editMaxPresion : EditText
    private lateinit var editMinPresion : EditText
    private lateinit var editMaxTemperatura : EditText
    private lateinit var editMinTemperatura : EditText
    private lateinit var pushProfileButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_newprofile)
        initAll()
        pushProfileButton.setOnClickListener{
            if(editMaxAlcohol.text.isEmpty() || editMinAlcohol.text.isEmpty() || editMaxAltitud.text.isEmpty() || editMinAltitud.text.isEmpty() || editMaxCO2.text.isEmpty()
                || editMinCO2.text.isEmpty() || editMaxHumedad.text.isEmpty() || editMinHumedad.text.isEmpty() || editMaxPresion.text.isEmpty() || editMinPresion.text.isEmpty()
                || editMaxTemperatura.text.isEmpty() || editMinTemperatura.text.isEmpty() || editNameProfile.text.isEmpty()){
                warningTextView.text = getString(R.string.warningMessage)
            }
            else{
                val profileToPush = Profile(dbPushProfile.push().key!!,editNameProfile.text.toString(),
                    Parameter(editMaxAlcohol.text.toString().toDouble(),editMinAlcohol.text.toString().toDouble()),
                    Parameter(editMaxAltitud.text.toString().toDouble(),editMinAltitud.text.toString().toDouble()),
                    Parameter(editMaxCO2.text.toString().toDouble(),editMinCO2.text.toString().toDouble()),
                    Parameter(editMaxHumedad.text.toString().toDouble(),editMinHumedad.text.toString().toDouble()),
                    Parameter(editMaxPresion.text.toString().toDouble(),editMinPresion.text.toString().toDouble()),
                    Parameter(editMaxTemperatura.text.toString().toDouble(),editMinTemperatura.text.toString().toDouble())
                    )
                dbPushProfile.child(profileToPush.id!!).setValue(profileToPush)
                finish()
            }
        }
    }

    private fun initAll() {
        editNameProfile = findViewById(R.id.editProfileName)
        editMaxAlcohol = findViewById(R.id.editMaxAlcohol)
        editMinAlcohol = findViewById(R.id.editMinAlcohol)
        editMaxAltitud = findViewById(R.id.editMaxAltitud)
        editMinAltitud = findViewById(R.id.editMinAltitud)
        editMaxCO2 = findViewById(R.id.editMaxCO2)
        editMinCO2 = findViewById(R.id.editMinCO2)
        editMaxHumedad = findViewById(R.id.editMaxHumedad)
        editMinHumedad = findViewById(R.id.editMinHumedad)
        editMaxPresion = findViewById(R.id.editMaxPresion)
        editMinPresion = findViewById(R.id.editMinPresion)
        editMaxTemperatura = findViewById(R.id.editMaxTemperatura)
        editMinTemperatura = findViewById(R.id.editMinTemperatura)
        pushProfileButton = findViewById(R.id.pushProfileButton)
        warningTextView = findViewById(R.id.warning_message)
        dbPushProfile = FirebaseDatabase.getInstance().getReference("Profiles")

    }

}